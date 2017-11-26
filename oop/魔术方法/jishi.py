import  datetime,time
def logger(fn):
    def wrapper(*args,**kwargs):
        start=datetime.datetime.now()
        ret=fn(*args,**kwargs)
        delta=(datetime.datetime.now()-start).total_seconds()
        print(delta)
        return ret
    return wrapper

#add =logger(add)
@logger
def add(x ,y):
    time.sleep(4)
    return  x+y

print(add(3,4))