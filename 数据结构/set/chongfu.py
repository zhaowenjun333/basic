import  random
alist=[]
blist=[]
for i in range(10):
    alist.append(random.randrange(10,21))
    blist.append(random.randrange(10,21))
print(" alist :\n",alist)
print(" blist:\n",blist)
uprepeatset=set()
uprepeatset.update(alist)
uprepeatset.update(blist)
print('in 20 nums ,unrepeat nums are below:\n{}'.format(uprepeatset))

aset=set(alist)
bset=set(blist)
repeat={}
repeat=aset.intersection(bset)
print('in 20 nums ,repeat nums are below:\n{}'.format(repeat))

