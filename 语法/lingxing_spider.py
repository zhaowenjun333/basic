"""第一种写法"""
# for i in range(-3,4):#变量i控制行数
#     if i>0:
#         prepace=i
#     else:
#         prepace = -i
#     print(" "*prepace+"*"*(7-prepace*2))


"""第二种写法"""
# for i in range(-3,4):#变量i控制行数
#     prepace=i if i>0 else -i
#     print(" "*prepace+"*"*(7-prepace*2))

"""第三写法"""
count=0
for i in range(-3, 4):  # 变量i控制行数
    count+=1
    prepace = i>0 and i or -i
    if count<=3:
        print(" " * prepace + "*" * count)
    elif count==4:
        print("" * prepace + "*" * (7 - prepace * 2))
    else:
        print(" " * prepace +" "*(3-i)+ "*" * (4 - prepace  ))



"""
三目运算符：
两种写法
一》
b if a else c
二》
a and b or c
等价于》
if a:
  d = b
else:
  d = c
"""

