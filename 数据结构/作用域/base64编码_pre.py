

a=int('0b0110101001010001010100',base=0)
print(a)
b= bin(a)
print(b)

a=int('0x10', 0)
print(a)

a=int('1010', 0)   #  字符串直接转
print(a)

a =int.from_bytes(b'31','big')

print(bytes(2))

a =int.from_bytes(b'\x31','big')
print(a)

a =int.from_bytes(b'31','big')      #3 和 1 这两个字符   对应的   二进制分别为   110011  00  110001=13105   那么他们两个组合就是十进制的13105
print(a)


a =int.from_bytes(b'a','big')
print(a)

inta =65
a =inta.to_bytes(3,'big')  #b'\x00\x00A'
print(a)