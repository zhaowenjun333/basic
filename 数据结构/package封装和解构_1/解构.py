def add(x,y):
    return x+y

def add1(a,b):
    return a+b




adict={'x':123,'y':456}
print(add(**adict))          # 这个使用 add1 是会报错的,因他会   解构为 kw 型的参数item,需要和 参数一致    x=3 ,y=3
print(add1(*adict.keys()))
print(add1(*adict.values()))

# print(add("a","b"))




