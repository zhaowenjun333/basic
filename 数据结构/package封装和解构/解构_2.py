def add(x,y):
    print(x,y)

add(*{3,4})
add(*[3,4])
add(*(3,4))
add(**{"x":4,"y":5})   # 结构为 x=4,y=5       所以可以传入一个函数def(x,y):pass
# print(x =9)   #'x' is an invalid keyword argument for this function
# print(**{"x":4,"y":5})  #'x' is an invalid keyword argument for this function