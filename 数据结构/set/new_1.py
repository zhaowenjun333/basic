seta = set(range(10))
print(seta)

seta.update({'a',"b","c"})
print(seta)

seta.update(range(20))      # 1后面跟上 可迭代对象即可,      2 可以是多个集合
print(seta)


# setb ={(1,24),b'al;jglwakjhlw',bytearray(b'jjkjkjkbbuuububufgfg')}    # 会抛异常  ,因为 bytearray 是可变的,所以    他是不可以 hash的
# print(setb)




