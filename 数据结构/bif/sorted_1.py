import random
#tuple
tuple_a = (1,2,3,34,66,7)
tuple_b= sorted(tuple_a)
print(tuple_b)
print(tuple_a)

#list
list_a = list((i  for i in range(10) if i %2==0)) # 生成器表达式
list_b = random.shuffle(list_a)
print(list_b)
print(list_a)
list_c =sorted(list_a,reverse=True)
print(list_c)

#set  集合
set_a = {1,4,7,9,0,6,5}
