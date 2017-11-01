def adder(x,y):
    return x+y

print(adder((1,2)[0],[3][0]))


print("________________________________")
t=(4,8)
print(adder(*t))
l =[1,2]       # 要和   形参的参数个数  相同
print(adder(*l))
print(adder(*{2,3}))


print(adder(*range(1,3)))
