lst0 = [1, [2, 3, 4], 5]
lst5 = lst0.copy()
print(lst5 == lst0)

lst5[2] = 10         #  简单数据 复制的时候是复制数值,所以修改的时候就是修改数值.
print(lst5 == lst0)

lst5[2] = 5
lst5[1][1] = 20       #修改了原有的 ,因为是复制的是引用地址  而不是自己开辟了一块空间,同一块空间,要改一起改
print(lst5)           # 都改变了
print(lst0)           # 都改变了
print(lst5 == lst0)

print("$$$$$$$$$$$$$$$$$$$$$$$")
lst0 = list(range(4))
lst2 = list(range(4))
print(lst0 == lst2)
# lst1 = lst0
# lst1[2] = 10