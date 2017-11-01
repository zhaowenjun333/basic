def counter(base=1):
    # tepparm=9
    def incc(step):
        return base+step
    return incc
a =counter(3)
print(a)

print(a(3))
# print(tepparm)

print(a(3))    #base 的数值一直存在 a
print(a(3))
print(a(3))