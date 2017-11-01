def foo(x=None):
    if x==None:
        x=[]
    x = x[:] #
    x.append(1)
    print(x)

lst=[1,2,4]
foo(lst)
print(lst)


def foo(x=None):
    if x==None:
        x=[]
    # x = x[:] #
    x.append(1)
    return x

lst=[1,2,4]
a =foo(lst)
print(a)
print(lst)