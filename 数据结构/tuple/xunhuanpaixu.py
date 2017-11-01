# alist=[]
# for i in range(3):
#     alist.append(int(input("请输入第"+str(i+1)+"个数子")))
# print("排序前",alist)
# if alist[0]>alist[1]:
#     alist[0],alist[1]=alist[1],alist[0]
# if alist[0]>alist[2]:
#     alist[0], alist[2] = alist[2], alist[0]
# if alist[1]>alist[2]:
#     alist[1], alist[2] = alist[2], alist[1]
# print("排序后:",alist)


# 第二种排序
#
# alist=[]
# for i in range(3):
#     alist.append(int(input("请输入第"+str(i+1)+"个数子")))
# print("排序前",alist)
# if alist[0]>alist[1]:
#     if alist[1]>alist[2]:
#         print(alist[2],alist[1],alist[0])
#     else:
#         if alist[0]>alist[2]:
#             print()
#         else:
#             print()
# else:
#     if alist[1]<alist[2]:
#         print( )
#     else:
#         if alist[0]>alist[2]:
#             print()
#         else:
#             print()
# 第三种排序\
alist=list()
for i in range(3):
    alist.append(int(input("请输入第"+str(i+1)+"个数子")))
for i  in range(3):
    a = min(alist)
    print(a)
    alist.remove(a)

