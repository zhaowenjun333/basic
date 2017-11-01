
#只要该 作用域    定义了 出现过 a = #   or    a =a +#
# 就说明定义了,

x = 5
def foo1():
    y=x+1
    print(y)

def foo2():
    # x=3
    b= x + 1
    x=5
    print(x)

def foo2():
    x+=1    #  -> x=x+1
    print(x)

foo1()
foo2()



