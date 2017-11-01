"""
 first
 yeild i
 second
 yeild from range(10)     后面是可迭代对象 iterable

"""
def inc():
    def counter():
        i = 0
        while True:
            i += 1
            yield i
    c = counter()
    return lambda : next(c)


foo = inc()
print(foo())
print(foo())