

"""
python是强类型的语言
1, 安照 顺序传入
2, 关键字传参 ,  个数不能少

组合,
def  f(x,y,z)


f(y= 5,z=6 ,2)  这个是不行的,x   函数里面    顺序是第一 ,那么调用的 时候 也需要 第一个  ,位置参数
位置对应
总结:严格按照位置第一原则调用,就不会发生这样的事情

def rf(x=3,y=6)
    return x+y

rf(4)  位置参数赋值给x 可以的


def F(x=2,y)    这样定义是错的,    位置参数必须在  关键字参数前面,简单来记作 简单在前

"""
def f(x,z,y=6):       # 缺省值  放在最后
    return x+y+z

print(f(2,6))
print("_________________________________________________")
def f1(num):
    sum=0
    for i in num:
        sum+=i
    return sum
print(f1([1,2,4,5,6]))

print("_________________________________________________")
def f2(*n):
    sum=0
    print(type(n))    # 元组,不可变类型的
    for x in n:
        sum+=x
    print(sum)
f2(2,3,5)    # 传参只能 是 位置   f2(n=3)
print("_________________________________________________")

## 所以关键字参数 传参方式 如下
def showconfig(**args):
    for k,v in args.items():
        print("{}={}".format(k,v))

showconfig(host='localhost',port="8080",username="zhaoyun")