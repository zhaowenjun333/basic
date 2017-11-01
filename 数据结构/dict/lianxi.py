
num= str(input('>>'))
dickdata= dict(enumerate(num))
alist=[0]*10
for i in dickdata:
    alist[int(dickdata[i])] +=1
count =0
for i  in alist:
    if i !=0:
        print("{} has appear {} times".format(count,i))
    count+=1
