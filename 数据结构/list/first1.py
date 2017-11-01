import math


alist=["w",2,2,2.4,True]
print(alist.count(2))  # 求 数值2的次数 , 列表中 2 的次数有两次


blist = list((1,2,4,5)) #  从元组   通过方法list()    转换为  列表
#  和int()    str()    等方法一样,可以从其他的数据类型转化为对应     还有一个诡异的range()  转化为range  类型
print(blist)

clist=list(blist)
print(clist)


# 求一个列表的长度
count =0
for i in  clist:
    count+=1
    print(i)
else:
    print("数组的长度为:",count)

print(clist.__len__())
print(len(clist))


