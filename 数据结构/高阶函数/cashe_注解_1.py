import functools,inspect
def add(x,y:int,*args,**kwargs) ->int:
    return x+y


a=inspect.isbuiltin(add)
a=inspect.isabstract(add)
a=inspect.ismethod(add)
print(a)
