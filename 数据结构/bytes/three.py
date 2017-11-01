print( 'vb'.encode().hex())       # 返回   7662   62是b
print(bytearray(bytes("abc",'utf-8')))

print(bytearray(bytes([10,2,3])))
print(bytearray(bytes([10,2,3])).hex())    #整数的16进制     看具体情况  ,1-9 是本身,     10-15 是 abcdef