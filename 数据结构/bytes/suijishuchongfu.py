import  random
alist=[]
for i in range(10):
    alist.append(random.randrange(1,21))
print("system give a list of  ten size num :\n",alist)
chongfulist=[]
buchongfulist=[]
for j in alist:
    l =alist.count(j)
    if  l== 1:
        buchongfulist.append(j)
    else:
        if 1>chongfulist.count(j):
            chongfulist.append(j)
print("repeat num have {}, here it is as below:".format(len(chongfulist)))
for n in chongfulist:
    print(n,end=" ")
print()
print("notrepeat num have {}, here it is as below:".format(len(buchongfulist)))
for k in  buchongfulist:
    print( k,end=" ")