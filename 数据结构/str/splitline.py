s1 = r"abaccbaba\nbacbababcccccabccc"                 # 加r 和不加的区别
s2 = """abaccbaba\nbacba
        babcccccabccc"""
print(s2.splitlines(True))                                 # return list of Strings

print(s2.splitlines())
