from functools import lru_cache
# import functools     # 这样的写法也是可以的导入方法
@lru_cache() # maxsize=None
def fib(n):
    if n < 2:
        return n
    return fib(n-1) + fib(n-2)
print([fib(x) for x in range(95)])