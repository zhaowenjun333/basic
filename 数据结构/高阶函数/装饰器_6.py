def copyproperties(src):
    def copywrapper(dst):
        dst.__name__=src.__name__
        dst.__doc__=src.__doc__
        return dst
    return copywrapper

def logger(fn):
    '''
    i am logger documents
    :param fn:
    :return: wrapper
    '''
    @copyproperties(fn)
    def wrapper(*args,**kwargs):
        '''
        :param args: ()
        :param kwargs: dict
        :return: None
        '''
        if type(args[0]) != type(args[1]):
            print("类型不一致请重试")
            return
        z = fn(*args, **kwargs)
        f = open('C:\\Users\\Public\\Documents\\test.txt', 'w')
        f.write(str(z))
        f.close()
        print("finish")

    # wrapper =copyproperties(fn)(wrapper)
    return wrapper

@logger # add1 = logger(add1)
def add(x,y):
    '''
    i am called add
    :param x:int
    :param y: int
    :return:int
    '''
    ret = x + y
    return ret

print(add.__name__)
print(add.__doc__)
print(logger.__doc__)
print(logger.__name__)