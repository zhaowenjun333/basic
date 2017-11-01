
def f2(**n):   #将关键字参数转化为 字典
    for k,v in n.items():
        print("{}.{}".format(k,v))


f2(a=2,b=4,c=5)    # 传参只能 是 位置   f2(n=3)

adct ={'a':2,'b':5,'z':7}#
f2(**adct)     #  实参的解构,将一个字典解构为   关键字参数  ,可以传给 f(a,b,z)  , f(**kwargs)