


for i in range(1,10):
    for j in range(i,10):
        #first
        # print(j, "*", i, "=", i * j, end="\t")
        #second
        print("  "*i,i,"*",j,"=",i*j,end="     ")
        if i*j<10:
            print(end=" " )
    print()