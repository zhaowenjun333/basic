import functools,inspect
def add(x,y:int,*args,**kwargs) ->int:
    return x+y

print(add(4,5))

params=inspect.signature(add)
print('这是一个方法的签名:',params)
print(params.parameters)
print(params.parameters['x'])
print(inspect.signature(add).parameters['x'].annotation)   # 无法自动提示
print("+++++++++++++++++++++++++++++++++++++++++++++++")
print(params.parameters['y'])
print(params.parameters['y'].annotation)
print(params.parameters['args'])
print(params.parameters['args'].annotation)
print(params.parameters['kwargs'])
print(params.parameters['kwargs'].annotation)

# a=OrderedDict([('x', "changkuaideyinyue"), ('y',  "hainihaoma")])     无法使用OrderedDict()
print("+++++++++++++++++++++++++++++++")
a=dict([('x', "changkuaideyinyue"), ('y',  "hainihaoma")])     # 可以使用OrderedDict()
print(a)
print(a['y'])
print(a['x'])

print("+++++++++++++++++++++++++++++++$$$$$$$$$$$$$$$$$$$$$$")
print(add.__annotations__)