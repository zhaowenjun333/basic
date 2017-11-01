import random
alist = [1, 6, 7, 3]
print(alist)

random.shuffle(alist)
print(alist)

alist.sort()
print(alist)

alist.reverse()
print(alist)

blist=alist.copy()
print(blist)

blist[1]=5
print(alist)
print(blist)


