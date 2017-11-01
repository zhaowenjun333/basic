import random

blist=[]
for j in range(101):
    alist = []
    for i in range(2):
        alist.append(random.choice("abcdefghijklmnopqrstuvwxyz"))
    blist.append(alist[0]+alist[1])
print(blist)

adict={}
for j in blist:
    if j not in adict:
        adict[j] = 1
    else:
        adict[j]+=1

sortlist=[]
for j in adict.keys():
    sortlist.append(j)
sortlist.sort(reverse=True)
# sortlist.reverse()
count =0
print("升序输出并统计个数为")
for k in sortlist:
    count +=1
    print("{},{}".format(k,adict[k]),end="  ")
    if count%10==0 :
        print()