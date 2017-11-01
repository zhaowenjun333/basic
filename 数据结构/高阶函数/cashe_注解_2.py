import functools,inspect
def add(x,y:int,*args,**kwargs) ->int:
    return x+y

print(add(4,5))

params=inspect.signature(add)
print('这是一个方法的签名:',params)
print(params.parameters)

print(params.parameters["y"].name)
print(params.parameters["y"].kind)
print(params.parameters["y"].default)
print(params.parameters["y"].name)
print("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{")
for k,v in enumerate(params.parameters.keys()) :
    print(">>",k)
    print(">>",params.parameters[v])
    print(v)

print("{{{{{{{{{{{{{^^^^^^^^^^^^^^^^^^^^{{{{{{{{{{{{{{{{{")
for k,(v,i) in enumerate(params.parameters.items()):
    print(k)
    print(v)
    print(i)
