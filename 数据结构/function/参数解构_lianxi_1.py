def printnum(n):
    for i in range(n,0,-1):
        print(i * "    ", end="")
        for j in range(n-i+1,0,-1):
            print(j,end="\t")
        print()


printnum(1800)