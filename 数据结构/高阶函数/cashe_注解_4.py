import inspect
def add(x, y:int=7) -> int:
    return x + y
def check(fn):
    def wrapper(*args, **kwargs):
        sig = inspect.signature(fn)
        params = sig.parameters
        values = list(params.values())
        for i,p in enumerate(args):

            if isinstance(p, values[i].annotation): #
                print('==')
        for k,v in kwargs.items():
            if isinstance(v, params[k].annotation): #
                print('===')
        return fn(*args, **kwargs)
    return wrapper


check(add)(20,10)