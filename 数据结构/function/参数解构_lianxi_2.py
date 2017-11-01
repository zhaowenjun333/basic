def printnum(n):
    for i in range(0,n):
        print(i*"        ",end="    ")
        for j in range(n-i,0,-1):
            print(j,end="\t\t")
        print()
printnum(12)



