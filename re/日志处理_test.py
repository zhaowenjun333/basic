from pathlib import Path
import datetime,glob,re
from user_agents import parse
from queue import Queue
import threading,time
ops = {
    'datetime': lambda timestr: datetime.datetime.strptime(timestr, '%d/%b/%Y:%H:%M:%S %z'),
    'status': int,
    'length': int,
    'request':lambda request:dict(zip(('method','url','protocol'),request.split())),
    'useragent':lambda useragent: parse(useragent)
}
pattern = '''(?P<remote>[\d.]{7,}) - - \[(?P<datetime>[/\w +:]+)\] \
"(?P<method>\w+) (?P<url>\S+) (?P<protocol>[\w/\d.]+)" \
(?P<status>\d+) (?P<length>\d+) .+ "(?P<useragent>.+)"'''
regex = re.compile(pattern)

def extract(line) -> dict:
    matcher = regex.match(line)
    info = None
    if matcher:
        info = {k:ops.get(k, lambda x:x)(v) for k,v in matcher.groupdict().items()}
    return info

def openfile(path:str):
    with open(path) as f:
        for line in f:
            record = extract(line)
            if record:
                yield record
            else:
                continue  # TODO 解析失败就抛弃，或者打印日志

def load(*files):
    """装载日志文件"""
    for item in files:
        p = Path(item)
        if p.is_file():
            yield from openfile(str(p))

def window(src:Queue, handler, width: int, interval: int):  # 每个线程都会走这个方法
    start = datetime.datetime.strptime('20170101 00:00:00 +0800', '%Y%m%d %H:%M:%S %z')
    current = datetime.datetime.strptime('20170101 01:00:00 +0800', '%Y%m%d %H:%M:%S %z')
    buffer = []  # 窗口中的待计算数据
    delta = datetime.timedelta(seconds=width - interval)
    while True:
        data = src.get()  # 如果没有阻塞住,程序一直停在 这里等待新日志的输入     等待生产者
        if data:  # 存入临时缓冲等待计算
            buffer.append(data)
            current = data['datetime']
        if (current - start).total_seconds() >= interval:     # 只有大于5秒才会进入这里处理
            ret = handler(buffer)
            print("{}".format(ret))
            start = current
            buffer = [x for x in buffer if x['datetime'] > current - delta]  # buffer里面还有剩余,重叠方案

def browser_handler(iterable):
    browsers = {}
    for item in iterable:
        ua = item['useragent']
        key = (ua.browser.family, ua.browser.version_string)
        browsers[key] = browsers.get(key, 0) + 1
    browsers["5秒内访问次数"]=sum(browsers.values())
    return browsers

def status_handler(iterable):  # 状态码占比
    # 一批时间窗口内的数据
    status = {}
    for item in iterable:
        key = item['status']
        if key not in status.keys():
            status[key] = 0
        status[key] += 1
    total = sum(status.values())
    status["访问次数"]=total
    return {k:(v//total*100 if k!="访问次数" else v ) for k,v in status.items()}


def dispatcher(src):
    threads=[]    ## 线程
    queues=[]     ##队列
    def reg(handler,width,interval):     ##注册服务
        q=Queue()
        queues.append(q)
        t =threading.Thread(target=window, args=(q,handler,width,interval))  #window()
        threads.append(t)

        for item in src:
            for q in queues:
                q.put(item)
    def run():
        for i in threads:
            i.start()

    return reg, run

if __name__=="__main__":
    files = [ i  for  i in glob.glob(r'C:\Users\zhaoyun\PycharmProjects\basic\re\*.log') ]
    reg,run=dispatcher(load(*files))
    reg(status_handler, 10, 5)  # 注册
    reg(browser_handler, 5, 5)
    run()