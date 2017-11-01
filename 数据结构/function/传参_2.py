def f2(*n):
    sum=0
    print(type(n))    # 元组,不可变类型的
    for x in n:
        sum+=x
    print(sum)
f2(2,3,5)    # 传参只能 是 位置   f2(n=3)