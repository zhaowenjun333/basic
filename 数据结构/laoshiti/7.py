lst =[375, 3.5, 6 ,20, 9, -20, 68]
len = len(lst)
for i in range(0,len):
    k=i
    for j in range(i+1,len):
        if lst[j] < lst[k]:
            k = j
    if lst[i] >lst[k]:
        lst[k],lst[i] =lst[i],lst[k]
print(lst)