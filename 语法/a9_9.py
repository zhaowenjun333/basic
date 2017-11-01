


for i in range(1,10):
    for j in range(1,i+1):
        #first
        # print(j, "*", i, "=", i * j, end="\t")
        #second
        print(j,"*",i,"=",i*j,end="     ")
        if i*j<10:
            print(end=" " )
    print()