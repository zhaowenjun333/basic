import datetime
print(datetime.datetime.now())
astring = input("input  a number ")
lst = list(astring)
print(lst)
blst=[0]*10
for i in lst:
    if int(i) in blst:
        blst[i] +=1

