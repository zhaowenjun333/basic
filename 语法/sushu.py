
import datetime
num=1000
if num>2:
     print(2)
if num>3:
     print(3)
if num > 5:
     print(5)
if num > 7:
    print(7)
for num in range(11,num+1,2):
    for i in range(3,int(num**0.5)+1,2):
        if num%i==0:
            break
    else:
        print(num)



