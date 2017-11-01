import random
mixlist = list(range(1,10))
random.shuffle(mixlist)
lenofml =len(mixlist)
for i in range(lenofml):
    k = i
    for j in range(i+1,lenofml):
        if mixlist[j] <mixlist[k]:
            k = j
    if mixlist[k] <  mixlist[i]:
        mixlist[k],mixlist[i]=mixlist[i],mixlist[k]
print(mixlist)


print("++++++++++++++++++++++++++++++++++++++++++++++")
mixlist = list(range(1,10))
random.shuffle(mixlist)
l = len(mixlist)
for i in range(1,l):
    for j  in range(1,l-i +1):
        if mixlist[j-1]>mixlist[j]:
            mixlist[j-1],mixlist[j]=mixlist[j],mixlist[j-1]
print(mixlist)
