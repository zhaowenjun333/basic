b = int.from_bytes("d\x00\x00".encode(), 'big')
print(b)



b = int(257).to_bytes(3, 'big')
print("big",b)

b = int(257).to_bytes(3, 'little')
print("litle",b)