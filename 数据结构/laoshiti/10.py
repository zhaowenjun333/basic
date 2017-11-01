baselist=[[1	,2,	6,	7],
            [3	,5,	8	,13],
            [4	,9,	12,	14],
            [10	,11	,15,	16]]
print(baselist)

printlist=[]
temlist =[]
for i in range(4) :
    for j in range(4):
        if i ==0 :
            printlist.append(baselist[i][j])
        elif   i== 1 and j ==3:
            printlist.append(baselist[i][3])
        elif i == 2 and j == 3:
            printlist.append(baselist[i][3])
        else:
            if i==3  :
                printlist.append(baselist[i][3-j])

        if  i== 1 and j ==0:
            temlist.append(baselist[i][j])
        elif  i== 2 and j ==0:
            temlist.append(baselist[i][j])
temlist.reverse()

for i in temlist:
    printlist.append(i )
print(printlist)