a =int("0101",2)
a=137
b=int(137).to_bytes(1,'big')
b=a.to_bytes(3,'big')
print(a)
print(b)