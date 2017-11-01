for i in range(1,10):
    for k in range(1,i):
        print(8*" ",end='')
    for j in range(i,10):
        print("{}*{}={}".format(j,i,j*i),end="\t")
    print()

print("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")

