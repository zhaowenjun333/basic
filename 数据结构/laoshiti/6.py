lst =[375, 3.5, 6 ,20, 9, -20, 68]

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