"""

这是最复杂的情况   ,  两个带参装饰器
"""

import datetime,time
def copyproperties(src):
    def copywrapper(dst):
        dst.__name__=src.__name__
        dst.__doc__=src.__doc__
        return dst
    return copywrapper
def logger(duration):
    def _logger(fn):
        @copyproperties(fn)
        def wrapper(*args,**kwargs):
            start=datetime.datetime.now()
            ret=fn(*args,**kwargs)
            delta=(datetime.datetime.now()-start).total_seconds()
            print("so slow ") if delta > duration else print("so fast")
            return ret
        # wrapper =copyproperties(fn)(wrapper)
        return wrapper
    return _logger

@logger(5) # add1 = logger(5)(add)
def add(x,y):
    time.sleep(4)
    return x + y

print(add(5,6))