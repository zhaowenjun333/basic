import math
src=b'YWJjZA=='
# src=b'YWJj'
# src=b'YWJjZGZna2Rl'
alphabet=b'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/'
def base64_decode(src):
    length=len(src)
    bytearray1=[]
    for j in range(0,length,4):
        strtarget = ''
        lista=list()
        for i in src[j:j+4]:
            if i != 61:
                ind=alphabet.index(i)
                lista.append(bin(ind)[2:].zfill(6))
        strtarget=''.join(lista)
        # strtarget=strtarget[:]
        by = int(strtarget, 2).to_bytes(math.ceil(len(strtarget) / 8), 'big')
        bytearray1.append(by)
    return bytearray1
a=base64_decode(src)
print(a)