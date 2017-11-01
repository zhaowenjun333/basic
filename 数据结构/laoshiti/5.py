alist =  [29, 30, 37, 22, 59, 75, 79, 41, 61, 75, 75, 78, 63, 52, 44, 20, 27, 29, 48, 66,
 60, 36, 67, 73, 41, 54, 66, 46, 74, 47, 61, 23, 38, 61, 51, 55, 48, 59, 28, 68,
 65, 74, 73, 58, 29, 31, 53, 31, 61, 22, 44, 33, 21, 41, 21, 35, 32, 59, 76, 32,
45, 78, 29, 65, 76, 70, 54, 22, 32, 52, 62, 42, 41, 73, 72, 64, 56, 50, 40, 64,
41, 47, 68, 73, 27, 69, 64, 21, 78, 57, 61, 27, 27, 66, 23, 21, 53, 40, 28, 64]
# first
adict = {}
for i in alist :
    if i not in adict:
        adict[i]=0
    adict[i]+=1
sorted(adict)

countturnnextline=0
for j in adict:
    print(j,adict[j],end=" ,")
    countturnnextline+=1
    if countturnnextline%10 ==0 :
        print()

print("\n++++++++++++++++++++++++++++++++++++++++++++++++++")
# second
countlist=[0]*100
for i in alist:
    if i not in countlist  and alist.count(i) ==1:
        countlist[i]=1
    else:
        countlist[i]+=1

countturnnextline=0
countindex=0
for j in countlist:
    if j !=0:
        print(countindex,j,end= " ,")
        countturnnextline+=1
        if countturnnextline % 10 == 0:
            print()
    countindex += 1