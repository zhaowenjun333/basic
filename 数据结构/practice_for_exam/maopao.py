import random
lst = list(range(1,10))
random.shuffle(lst)
print(lst)
len = len(lst)
for i in range(len):
    count = 0
    for j in range(len-i-1):
        if lst[j]>lst[j+1]:
            count+=1
            lst[j],lst[j+1]=lst[j+1],lst[j]
    if count <1:
        break
print(lst)