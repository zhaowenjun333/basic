count =1
lastlist = []
for i in range(4):
    templist = []
    for j in range(4):
        print("{0:>4}".format(count),end=" ")
        templist.append(count)
        count+=1
    print()
    lastlist.append(templist)

print("++++++++++++++++++++++++++++++++++++")

for i in range(4):
    for j in range(4):
        print(lastlist[j][i],end="\t")
    print()



