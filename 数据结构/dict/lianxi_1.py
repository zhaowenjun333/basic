import random
alist =[]
for i in range(101):
    alist.append(random.randrange(-1000,1001))
print(alist)
adict={}
for j in alist:
    if j not in adict:
        adict[j] = 1
    else:
        adict[j]+=1
sortlist=[]
for j in adict.keys():
    sortlist.append(j)
sortlist.sort()
for k in sortlist:
    print("升序输出为{},{}".format(k,adict[k]))