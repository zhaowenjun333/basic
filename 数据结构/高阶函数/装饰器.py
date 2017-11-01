def add(x,y):
    return x+y

def add(x,y):
    return "call{},{}+{}".format(add.__name__,x,y)  #  下划线 是 类变量直接用

def fn(x,y):
    return x+y

def logger(fn):
    print()
    x=fn(4,5)
    print()

