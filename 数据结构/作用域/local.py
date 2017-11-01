a=[2]
b=a[:]  #切片为新的 列表  序列squ
b.append(4)
print(a)
print(b)


import copy
lst0 = [1, [2, 3, 4], 5]
lst5 = copy.deepcopy(lst0)
lst5[1][1] = 20
print(lst5 == lst0)

lst0 = [1, [2, 3, 4], 5]
lst5 = lst0.copy()
lst5[1][1] = 20
print(lst5 == lst0)

lst0 = [1, 2, 5]
lst5 = lst0.copy()
lst5[1] = 20
print(lst5 == lst0)

lst0 = [1, 2, 5]
lst5 = copy.deepcopy(lst0)
lst5[1] = 20
print(lst5 == lst0)