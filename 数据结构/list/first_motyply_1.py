# blist =[1,2,4]
# c=blist+[3]
# c=blist+list((6,))
# print(c)


lst0 = list(range(4))
lst2 = list(range(4))
print(lst0==lst2)

lst1 = lst0
lst1[2] = 10
print(lst0==lst2)