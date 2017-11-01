import inspect
def add(x,y:int=8) ->int:
    return x+y

def check(fn):
    def wrapper(*args,**kwargs):
        signature=inspect.signature(add)
        allparams=signature.parameters      # 有序字典
        print(allparams.keys())            #odict_keys
        print(allparams.values())           #odict_values
        print(allparams.items())            #odict_items
        values=list(allparams.values())      #这里是个坑 ,需要转换为list才可以根据下标取值,getVaules()
        for i ,j in enumerate(args):
            print(i)

            print(values[i])
            if isinstance(j,(values[i]).annotation):
                print("++++++")
        for k,v in kwargs.items():
            if isinstance(v,allparams[k].annotation):
                print("pass")
        return fn(*args,**kwargs)
    return wrapper


print(check(add))
print(check(add)(4,7))