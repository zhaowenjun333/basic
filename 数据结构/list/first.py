import math

print(math.floor(2.7))

print(math.ceil(2.2))



print(type("a"))

print(type(type(5)))

alist=["w",2,2,2.4,True]
print(alist.index(1))  # 求 数值1 的索引 , 列表中只有True 是 1    bool 继承 int

ls=list(range(3))
ls.append("wlet") #   后面叠加一个
ls.remove(0)
print(ls)
print(ls.count(2))


arry=(1,24,5)
print(arry)


aint=153535
bstr="aljgwh"
clist=[alist,bstr]
print(clist)

aset={1,3,"lcdlls"}
print(aset)
aset.add(1)
print(aset)   #说明了是无序的且不能重复


arrlist = [1,2,4,5,5,7,8,9]
print(arrlist[-1])
# print(arrlist.__add__(0))
# print(arrlist[-1])

