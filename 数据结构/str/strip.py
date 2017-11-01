s1 = r"abaccbaba\nbacgffbababcccccabccc"                 # 加r 和不加的区别
s2 = "abaccbaba\nbacbababcccccabccc"
print(s1.strip("abc"))                        # return list of string
print(s1.split("abc"))
print(s1.partition("abc"))