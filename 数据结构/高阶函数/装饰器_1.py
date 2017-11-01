
def add(x,y):
    if type(x)!=type(y):
        print("类型不一致请重试")
        return
    z=x+y
    f=open('C:\\Users\\Public\\Documents\\test.txt','w')
    f.write(str(z))
    f.close()
    print("finish")
# add(2,3)

def add(x,y):
    return x+y

def logger(fn):
    def wrapper(*args,**kwargs):
        if type(args[0]) != type(args[1]):
            print("类型不一致请重试")
            return
        z = fn(*args, **kwargs)
        f = open('C:\\Users\\Public\\Documents\\test.txt', 'w')
        f.write(str(z))
        f.close()
        print("finish")
    return wrapper

logger(add)(4,1)