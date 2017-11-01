def copyproperties(src,dst):

    dst.__name__=src.__name__
    dst.__doc__=src.__doc__


def logger(fn):
    def wrapper(*args,**kwargs):
        """
        woshi wrapper
        :param args:
        :param kwargs:
        :return:
        """
        if type(args[0]) != type(args[1]):
            print("类型不一致请重试")
            return
        z = fn(*args, **kwargs)
        f = open('C:\\Users\\Public\\Documents\\test.txt', 'w')
        f.write(str(z))
        f.close()
        print("finish")
    # copyproperties(fn,wrapper)
    return wrapper

@logger # add1 = logger(add1)
def add(x,y):
    ret = x + y
    return ret

add(12,56)

print(add.__name__)
print(add.__doc__)