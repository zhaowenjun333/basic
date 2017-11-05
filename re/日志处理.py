import random
import datetime
import time
from queue import Queue
import threading
import re
from pathlib import Path
from user_agents import parse
# 数据源
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
    """返回字段的字典，如果返回None说明匹配失败"""
    matcher = regex.match(line)
    info = None
    if matcher:
        info = {k:ops.get(k, lambda x:x)(v) for k,v in matcher.groupdict().items()}
    return info

def openfile(path:str):
    with open(path) as f:
        for line in f:
            fields = extract(line)
            if fields:
                yield fields
            else:
                continue  # TODO 解析失败就抛弃，或者打印日志

def load(*paths):
    """装载日志文件"""
    for item in paths:
        p = Path(item)
        if not p.exists():
            continue
        if p.is_dir():
            for file in p.iterdir():
                if file.is_file():
                    yield from openfile(str(file))
        elif p.is_file():
            yield from openfile(str(p))

def source():
    while True:
        yield {'value':random.randint(1,100),'datetime':datetime.datetime.now().astimezone()}
        time.sleep(1)

def window(src:Queue, handler, width:int, interval:int):             #每个线程都会走这个方法
    start = datetime.datetime.strptime('20170101 00:00:00 +0800','%Y%m%d %H:%M:%S %z')
    current = datetime.datetime.strptime('20170101 01:00:00 +0800','%Y%m%d %H:%M:%S %z')
    buffer = [] # 窗口中的待计算数据
    delta = datetime.timedelta(seconds=width-interval)
    while True:
        data = src.get()       #如果没有阻塞住,程序一直停在 这里等待新日志的输入     等待生产者
        if data: # 存入临时缓冲等待计算
            buffer.append(data)
            current = data['datetime']
        if (current - start).total_seconds() >= interval:
            ret = handler(buffer)
            print("{}".format(ret))
            start = current
            # 重叠方案
            buffer = [x for x in buffer if x['datetime'] > current - delta]    #buffer里面还有剩余

def handler(iterable):
    vals = [x['value'] for x in iterable]
    return sum(vals) / len(vals)

def browser_handler(iterable):
    browsers = {}
    for item in iterable:
        ua = item['useragent']
        key = (ua.browser.family, ua.browser.version_string)
        browsers[key] = browsers.get(key, 0) + 1
    return browsers
# 状态码占比
def status_handler(iterable):
    # 一批时间窗口内的数据
    status = {}
    for item in iterable:
        key = item['status']
        if key not in status.keys():
            status[key] = 0
        status[key] += 1
    total = sum(status.values())
    return {k:v/total*100 for k,v in status.items()}

def dispatcher(src):       # 控制器 分发器 调度器
    handlers = []
    queues = []
    def reg(handler, width, interval):
        """ 注册窗口处理函数  :param handler: 注册的数据处理函数 :param width: 时间窗口宽度  :param interval: 时间间隔"""
        q = Queue()
        queues.append(q)
        h = threading.Thread(target=window, args=(q, handler, width, interval))
        handlers.append(h)
    def run():
        for t in handlers:
            t.start() # 启动线程   启动的后就立马执行    然后阻塞住了.等待往每个线程里面放 字符串,即下面的
        for item in src:
            for q in queues:
                q.put(item)
    return reg, run
if __name__ == "__main__":
    path = 'test.log'
    reg, run = dispatcher(load(path))
    reg(status_handler, 10, 5)  # 注册
    reg(browser_handler, 5, 5)
    run()

