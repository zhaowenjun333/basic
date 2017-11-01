
for i in range(1, 10):
        print("{0}".format(i*"        "),end="\t")
        for j in range(i, 10):
            print('{}x{}={}'.format(i, j, i*j), end='\t')
        print()





# for i in range(1,10):
#     # print((i-1)*("               "),end="")
#     for j in range(i,10):
#         print("{}*{}={}".format(i,j,i*j))
#     print()