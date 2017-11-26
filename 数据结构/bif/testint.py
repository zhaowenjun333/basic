
"""
 def to_bytes(self, length, byteorder, *args, **kwargs): # real signature unknown; NOTE: unreliably restored from __doc__

        int.to_bytes(length, byteorder, *, signed=False) -> bytes

"""
a =int('0b100', base=2)          #int('0b100', base=2)
print(a)
a =int("0101",2)
print(a)
a=137
b=int(137).to_bytes(1,'big')
print(b)
b=a.to_bytes(3,'big')
print(a)
print(b)