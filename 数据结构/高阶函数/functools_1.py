'''
一个奇特的问题,文件的命名千万不能和关键字函数之类的同名,否则报错
文件一开始被我命名 为  functools   出现了报错的情况

'''
import functools
print(3)
def add(x,y):
    return x+y

newadd=functools.partial(add,y=4)

print(newadd(5))
# print(add(3,4))
