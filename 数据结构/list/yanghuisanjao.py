


lst0=[[1],[1,1]]
for i in  range(2,6):
    pre=lst0[i-1]
    cur=[1]
    for j in range(len(lst0)-1):
        temp =pre[j]+pre[j+1]
        cur.append(temp)
    cur.append(1)
    lst0.append(cur)
print(lst0)