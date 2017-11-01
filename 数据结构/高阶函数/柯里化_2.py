def add(x,y):
    return x+y


"""
柯里化
"""
def add1(x):
    def add2(y):
        return x+y
    return add2


print(add1(2))
