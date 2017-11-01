lst=[1,(2,3,4),5]
_,a,_=lst
print(a)
*_,target=lst
print(target)

a,b = 1,2
print(a)
print(b)

a,b = (11,12)
print(a)
print(b)

a,b = [22,23]
print(a)
print(b)

a,b = {33,34}
print(a)
print(b)

# a,b = {33,34,4}   # 个数需要对应
print(a)
print(b)

a,b = {"a":43,"b":44}
print(a)
print(b)
print("####################")
a1,*b1=(1,2)     # {1,2}  [1,2]  结果都一样说明是   封装的时候 发生的事情
print(a1)
print(b1)