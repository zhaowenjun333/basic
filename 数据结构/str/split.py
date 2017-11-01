s1 = r"abaccbaba\nbacbababcccccabccc"                 # 加r 和不加的区别
s2 = "abaccbaba\nbacbababcccccabccc"
print(s1.split("c"))                        # return list of string
print(s2.split("c",maxsplit=2))                        # return list of string
print(s1.split("c"))
print(s2.split("c"))

print(s1.rsplit("c"))                               # 从右 到左分割
