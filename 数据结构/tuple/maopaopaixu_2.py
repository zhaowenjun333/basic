import  random
alist = list(range(1,10))
random.shuffle(alist)
print("排序前:",alist)

# 第一次循环
# num=len(alist)
# for i in range(1,num):
#     if alist[i-1] > alist[i]:
#         alist[i-1], alist[i] = alist[i], alist[i-1]
# print(alist)

#优化后的循环
num=len(alist)
for j in range(1,num):
    count = 0
    for i in range(1,num -j +1):    #这一步是关键
        if alist[i-1] > alist[i]:
            count =+ 1
            alist[i-1], alist[i] = alist[i], alist[i-1]
    if count ==0 :
            break
print(alist)
