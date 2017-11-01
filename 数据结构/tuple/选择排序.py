#选择排序
import  random
alist = list(range(1,10))
random.shuffle(alist)
print("排序前:",alist)
num=len(alist)

for i in range(0,num):
    k = i
    for j in range(i+1,num ):
        if alist[j] < alist[k]:
            k=j
    if alist[i] > alist[k]:
        alist[i], alist[k]= alist[k],alist[i]
print(alist)
