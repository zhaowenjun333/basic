for i in range(1,8):
    if i%2==0:
        continue
    for k in range(1,(7-i)//2+1):
        print(" ",end="")
    for j in range(8-i,8):
        print("*",end="")
    print()
for i in range (1,4):
    print( (i-1)*" ",((4-i)*2-1)*"*")

##误入歧途