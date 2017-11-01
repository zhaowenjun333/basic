import math
src='TWFU'
src='YWJjZGVm'.encode()
# src=b'YWJj'
# src=b'YWJjZGZna2Rl'
alphabet=b'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/'
def base64_decode(src:bytes):
    length=len(src)
    bytearray1=bytearray()
    for j in range(0,length,4):
        tem=0
        block =src[j:j+4]
        for i,c in enumerate(reversed(block)):
            index=alphabet.find(c)
            if index==-1:
                continue
            tem+= index << i*6
        bytearray1.extend(tem.to_bytes(3,'big'))
    return bytes(bytearray1.rstrip(b'\x00'))


a=base64_decode(src).decode()
print(a)