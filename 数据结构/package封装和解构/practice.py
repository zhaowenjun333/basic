lst=[1,(2,3,4),5]
_,a,_=lst
print(a)
*_,target=lst
print(target)

a,b = 1,2
print(a)
print(b)

a,b = (11,12)  # 集合
print(a)
print(b)

a,b = [22,23]#列表
print(a)
print(b)

a,b = {33,34}# 集合
print(a)
print(b)

# a,b = {33,34,4}   # 个数需要对应
print(a)
print(b)

a,b = {"aa":499993,"bb":44} # 字典结构得到的是 key
print(a)
print(b)
print("####################")
a1,*b1=(1,2)     # {1,2}  [1,2]  结果都一样说明是   封装的时候 发生的事情
print(a1)
print(b1)  #  *封装为列表,

ab=1,2      # 普通的封装为元祖
print(type(ab),ab)