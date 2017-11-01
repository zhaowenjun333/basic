for i in range(1,10):
    for j in range(1,i+1):
        print("{}*{}={}".format(j,i,j*i),end="\t")
    print()

print("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")

for i in range(1,10):
    for j in range(1,i+1):
        print("{0}*{1}={2:>3} ".format(j,i,j*i),end=" ")
    print()