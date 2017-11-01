import re,datetime


# (?<name>[\d\.]{7,})\s-\s-\s\[([^\[\]]+)\]\s".*?"\s(\d{3})\s(\d{5})\s"-"\s"(.*?)"
pattern = '''(?P<remote>[\d\.]{7,}) - - \[(?P<datetime>[^\[\]]+)\] "(?P<request>[^"]+)" \
(?P<status>\d+) (?P<size>\d+) "[^"]+" "(?P<useragent>[^"]+)"'''
regex = re.compile(pattern)
ops = {
       'datetime': lambda timestr: datetime.datetime.strptime(timestr, "%d/%b/%Y:%H:%M:%S %z"),
       'status': int,
       'size': int,
       'request': lambda request: dict(zip(('method', 'url', 'protocol'), request.split()))
   }
def fingsubstr(total:str):
   if regex.match(total):
       return {k: ops.get(k, lambda x: x)(v) for k, v in regex.match(total).groupdict().items()}


with open('test.log') as f:
    for logline in  f:
        # logline = '''183.60.212.153 - - [19/Feb/2013:10:23:29 +0800] "GET /o2o/media.html?menu=3 HTTP/1.1" 200 16691 "-" "Mozilla/5.0 (compatible; EasouSpider; +http://www.easou.com/search/spider.html)"'''
        print(fingsubstr(logline))
