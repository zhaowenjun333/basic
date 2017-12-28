"""
Number（数字）
    int（整型）
    float（浮点型）
    complex（复数）
    bool（布尔）
String（字符串）
List（列表）
Tuple（元组）
Sets（集合）
Dictionary（字典）
"""
lst0 = [1, [2, 3, 4], 5]
lst1 = lst0.copy()
print(lst1 == lst0)    # 数值比较
print(lst1 is lst0)    # 地址比较
print(id(lst0),id(lst1))

# lst0[1] = [2,3,4]
# print(lst0)
lst0[1][0] =3
print(lst0)
print(lst1)
print(lst1 ==lst0)