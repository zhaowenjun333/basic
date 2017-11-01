def copy_properties(src,dst):
    dst.__name__ = src.__name__
    dst.__doc__ = src.__doc__
    dst.__qualname__ = src.__qualname__

def lgger1(fn1):
    def wrapper(*arg,**kwargs):
        print("woshi")
        fn1(*arg,**kwargs)
    return wrapper
@lgger1
def logger(fn):
    def wrapper(*args,**kwargs):
        '''This is a wrapper'''
        print('before')
        ret =  fn(*args,**kwargs) # add3(4,5,6,y=6,z=5)
        print('after')
        return ret
    copy_properties(fn,wrapper)
    # @lgger1
    # copy_properties(fn,wrapper)
    return wrapper


@logger # add1 = logger(add1)
def add(x,y):
    '''
    This is a function
    return int
    x int
    y int
    '''
    ret = x + y
    return ret

print(add.__name__, add.__doc__, add.__qualname__,sep='\n')





