atuple=1,2,3
print(atuple[1])

btuple=(1,2,3,4)
print(btuple)
print(btuple[3])

dtuple=(2,)          #定义单个元祖的时候,需要加逗号
print(type(dtuple))

ctuple=(2)  #   等价于ctuple=2
print(type(ctuple))   ## 解释器会看成整型

etuple=tuple(range(4))      #这个和list一样,后面放置可以迭代的对象
print(etuple)

ftuple=tuple([1,2,3,4,5])
print(ftuple)

gtuple=tuple("woaini")
print(gtuple)

# htuple=tuple(12325)
# print(htuple)

str_a =str(gtuple)
print(str_a)
print(tuple(str_a))

