matrixlist=[
    [1,2,3],
    [4,5,6],
    [7,8,9]
]

# for i in matrixlist:
#     for j in i :
#         print('{}'.format(j))


for i in range(3):
    for j in range(3) :
        print('{}'.format(matrixlist[j][i]), end=" ")
        # if i<j:
        #     print('{}'.format(matrixlist[j][i]),end=" ")
        # else:
        #     print("{}".format(matrixlist[j][i]),end=" ")
    print()