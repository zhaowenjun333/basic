import  random
alist = list(range(1,10))
random.shuffle(alist)
print("排序前:",alist)

# 第一次循环
for i in range(1,9):
    if alist[i-1] > alist[i]:
        alist[i-1], alist[i] = alist[i], alist[i-1]
print(alist)
