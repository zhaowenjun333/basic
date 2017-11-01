seta = set([1,19,2,18,4,48,29,4,29,2,3])
print(seta)

seta.update("zhaoyun","a","b","c","d","e","f","g")
print(seta)
print("zhaoyun" in seta)

seta.add("zhaoyun")
print("zhaoyun" in seta)
print(seta)


c = seta.difference("zhaoyun")            #  两个 集合  相减 (可迭代的对象),而不是 直接减去本身
print(c)
z = seta.union({"jakwjg09"})         #  求两个集合的   或运算 |
print(z)
