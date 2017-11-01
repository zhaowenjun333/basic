import  random
alist = list(range(1,10))           #可以用   random 的 choice()   和 randrange()    利用循环来生成 一个  list
random.shuffle(alist)
print("排序前:",alist)

# 第一次循环
# num=len(alist)
# for i in range(1,num):
#     if alist[i-1] > alist[i]:
#         alist[i-1], alist[i] = alist[i], alist[i-1]
# print(alist)

# 外部循环控制  找最大值的 循环次数
num=len(alist)
for j in range(1,num):
    for i in range(1,num -j +1):    #这一步是关键
        if alist[i-1] > alist[i]:
            alist[i-1], alist[i] = alist[i], alist[i-1]
print(alist)
