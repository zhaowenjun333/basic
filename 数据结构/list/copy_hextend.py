lst0 = [1, [2, 3, 4], 5]
lst5 = lst0.copy()
print(lst5 == lst0)

lst5[2] = 10         #  简单数据 复制的时候是复制数值,所以修改的时候就是修改数值.
print(lst5 == lst0)

lst5[2] = 5
lst5[1][1] = 20       #修改了 所有的 ,因为是复制的是引用地址  而不是自己开辟了一块空间
print(lst5)
print(lst0)
print(lst5 == lst0)

