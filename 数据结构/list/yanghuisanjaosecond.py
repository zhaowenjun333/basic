# 把第一行第二行,分离出来

# lst0=[[1],[1,1]]
# for i in  range(2,6):
#     cur=[]
#     newpre=lst0[i-1].copy()
#     newpre.append(0)
#     newpre.insert(0, 0)
#     for j in range(len(newpre)-1):
#         temp =newpre[j]+newpre[j+1]
#         cur.append(temp)
#     lst0.append(cur)
# print(lst0)


# 第二种方法 合并第一,二项 到循环里面

lst0=[]
n=6
for i in  range(0,n):
    if i == 0:
        lst0.append([1])
    else:
        cur=[]
        preold=lst0[i-1]
        newpre=preold.copy()
        newpre.append(0)
        newpre.insert(0, 0)
        for j in range(len(newpre)-1):
            temp =newpre[j]+newpre[j+1]
            cur.append(temp)
        lst0.append(cur)
print(lst0)