# def add(x,y):
#     print(x+y)
# print(add.__name__)               #方法也有名字


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
def logger(duration,fun=lambda name,duration:print("{} took {}s".format(name,duration))):
    def _logger(fn):
        @copyproperties(fn)
        def wrapper(*args,**kwargs):
            start=datetime.datetime.now()
            ret=fn(*args,**kwargs)
            delta=(datetime.datetime.now()-start).total_seconds()
            # print("so slow ") if delta > duration else print("so fast")
            if delta> duration:
                fun(fn.__name__,duration)
            return ret
        # wrapper =copyproperties(fn)(wrapper)
        return wrapper
    return _logger

@logger(3) # add1 = logger(5)(add)
def add(x,y):
    time.sleep(4)
    return x + y

print(add(5,6))