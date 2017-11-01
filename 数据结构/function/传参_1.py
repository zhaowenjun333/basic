def showconfig(username,password,*r,**args):
    print(username)
    print(password)
    print(args)
    print(r)
showconfig(2,2,5,6,7,a=4,b=5)



def f2(*n):
    sum=0
    print(type(n))    # 元组,不可变类型的
    for x in n:
        sum+=x
    print(sum)

f2(a=2,b=3)
