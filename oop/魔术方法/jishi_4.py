import time
import datetime
from functools import wraps
class TimeIt:
    def __init__(self, fn):
        print('init')
        self._fn = fn
        wraps(fn)(self)
    def __enter__(self):
        print('enter')
        self.start = datetime.datetime.now()
        return self
    def __call__(self, *args, **kwargs):
        print('__call__')
        start = datetime.datetime.now()
        ret = self._fn(*args, **kwargs)
        delta = (datetime.datetime.now() - start).total_seconds()
        print("dec {} took {}".format(self._fn.__name__, delta))
        return ret

    def __exit__(self, exc_type, exc_val, exc_tb):
        print('exit')
        delta = (datetime.datetime.now() - self.start).total_seconds()
        print("context {} took {}".format(self._fn.__name__, delta))
        return

# def logger(fn):
#     @wraps(fn)
#     def wrapper(*args, **kwargs):
#         start = datetime.datetime.now()
#         ret = fn(*args, **kwargs)
#         delta = (datetime.datetime.now() - start).total_seconds()
#         print("dec {} took {}".format(fn.__name__, delta))
#         return ret
#     return wrapper
# @wraps(TimeIt)
@TimeIt
def add(x,y): # add = TimeIt(add)        #生成一个TimeIt对象,如果想要对象可以调用则需要重写魔术方法__call()__
    """This is a add function"""
    time.sleep(2)
    return x + y

print(add(10,11))

print(add.__doc__)
print(add.__name__)
# with TimeIt(add) as foo:
#     print(foo(5, 16))














