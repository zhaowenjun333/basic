


for i in range(1,10):
    print((i-1)*("               "),end="")
    for j in range(i,10):
        print(i,"*",j,"=",i*j,end="     ")
        if i*j<10:
            print(end=" " )
    print()