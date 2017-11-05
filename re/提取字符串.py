import re,datetime
# (?<name>[\d\.]{7,})\s-\s-\s\[([^\[\]]+)\]\s".*?"\s(\d{3})\s(\d{5})\s"-"\s"(.*?)"
pattern = '''(?P<remote>[\d\.]{7,}) - - \[(?P<datetime>[^\[\]]+)\] "(?P<request>[^"]+)" \
(?P<status>\d+) (?P<size>\d+) "[^"]+" "(?P<useragent>[^"]+)"'''
regex = re.compile(pattern)
ops = {
       'datetime': lambda timestr: str(datetime.datetime.strptime(timestr, "%d/%b/%Y:%H:%M:%S %z")),
       'status': int,
       'size': int,
       'request': lambda request: dict(zip(('method', 'url', 'protocol'), request.split()))
   }
def fingsubstr(total:str):
   if regex.match(total):
       return {k: ops.get(k , lambda x: x)(v) for k, v in regex.match(total).groupdict().items()}

def openfile(file):
    with open(file) as f:
        for logline in  f:
            yield  fingsubstr(logline)


for i  in  openfile('test.log'):
    print(i)




