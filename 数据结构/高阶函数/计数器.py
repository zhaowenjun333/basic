def counter(base):
    def incc(step=1):      #内部对象,相当于参数
        nonlocal base      # 如果没有这个申明就会报错  ,  base 在这个作用域没有赋值初始化
        base+=step
        return base
    return incc

foo=counter(10)
foo1=counter(10)
print(foo == foo1)    #print(counter(10)==counter(10))
print(foo())
print(foo())
print(foo())
print(foo1())