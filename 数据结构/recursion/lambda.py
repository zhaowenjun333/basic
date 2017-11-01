# 匿名函数

"""
规则:  要么加括号后再调用
        要么只定义不调用
"""

# a =lambda  x:x**2(4)    #  错误的写法


a =(lambda  x:x**2)(4)
print(a )


b= lambda x:x*2
print(b((1,2)))

def b(x):
    return  x*2
