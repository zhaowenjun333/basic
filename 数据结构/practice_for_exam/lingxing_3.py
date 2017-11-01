for i in range(-3,4):  ##误入歧途
    flag=0
    if i <0 :
        flag=-i
    else:
        flag=i
    print((2*(4-flag)-1)*"*")