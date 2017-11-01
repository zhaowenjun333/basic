s1 = "I'am  a  super student   "
print(s1.partition("s"))            # 返回 元组
print(s1.partition("super"))        # 和split  不一样,保留中间的
print(s1.partition(" "))        # 和split  不一样,保留中间的

print(s1.split("super"))             # 返回的是一个  list列表
