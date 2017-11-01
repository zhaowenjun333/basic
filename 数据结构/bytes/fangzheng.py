
# # 正常的打印
# for j in range(1,7):
#     print(j,end=" ")
#     if j % 3 ==0:
#         print()
# print("------------------------------------")
# # -------------------------------------
# # 竖着dayin
# for j in range(1,7):
#     print(j,end=" ")
#     if j % 2 ==0:
#         print()
# 正常的
# 123
# 456
count= 1
for i in range(2):
    for j in range(3):
        print(count,end=" ")
        count+=1
    print()

print("------------------------------------")
# 竖着打

c= 1
for i in range(3,0,-1):
    for j  in range(1,3):
        if j % 2==0 :
            print(7-i,end=" ")
        else:
            print(c,end=" ")
            c+= 1
    print()
print("======================================")
# 正常的
# 123
# 456
# 789
count= 1
for i in range(3):
    for j in range(3):
        print(count,end=" ")
        count+=1
    print()
# 竖着打
print("------------------------------------")
c= 1
for i in range(3,0,-1):
    for j  in range(1,4):
        if j % 2==0 :
            print(7-i,end=" ")
        elif j % 3==0:
            print(10 - i, end=" ")
        else:
            print(c,end=" ")
            c+= 1
    print()