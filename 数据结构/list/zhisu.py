
import datetime
num=100
list1=[2,3,5,7]
# if num>2:
#      # print(2)
#      list1.append(2)
# if num>3:
#      # print(3)
#      list1.append(3)
# if num > 5:
#      # print(5)
#      list1.append(5)
# if num > 7:
#     # print(7)
#     list1.append(7)
for num in range(11,num+1,2):
    for i in list1:
        if num%i==0:
            break
    else:
        # print(num)
        list1.append(num)
print(list1)


