"""


返回的是一个迭代器
iterable
"""

set_a={3,4,6,7,8,9}
# print(reversed(set_a))

print(reversed("abcdefghijklmnopqrstuvwxyz"))
print(reversed((1,2,3,5,6)))
print(reversed([1,2,5,6,8]))
print(reversed(bytes(123)))
print(reversed(b'126'))
print(reversed(bytearray("25jlwjl".encode())))
print(reversed(bytearray( range(10))))
for i in reversed(bytearray( range(10))):
    print(i)
    print(type(i))

print("________________________________")
set_a="abcdefghijklmnopqrstuvwxyz"
b=reversed(set_a)
print(set_a)
print(b)
for i in b:
    print(i)