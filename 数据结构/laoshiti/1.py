import  random
aset = {5,10,3,8,6,10,9,15,24,30,27,48,24}
alist = list(aset)
sum = 0
count = 0
while True:
    i= random.choice(alist)
    if i %3==0 and i %4 !=0 :
        print(i,end=" ")
        sum += i
        count +=1
    if count ==10:
        break
print("\nthe count of ten nums is : ",sum)