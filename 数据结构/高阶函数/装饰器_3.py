def logger(fn):
    def wrapper(*args,**kwargs):
        x=fn(*args,**kwargs)
        return x
    return wrapper

@logger
def add(x,y):
    return x+y

print(add(3,6))