src=b'YWJjZA=='
# src=b'YWJjZGZna2Rl'
alphabet=b'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/'
def base64_decode(src):
    length=len(src)
    bytearray1=[]
    for j in range(0,length,4):
        strtarget = ''
        lista=list()
        for i in src[j:j+4]:
            if i !="=":
                ind=alphabet.index(i)
                lista.append(bin(ind)[2:].zfill(6))
        strtarget=''.join(lista)
        strtarget=strtarget[:32]
        by = int(strtarget, 2).to_bytes(len(strtarget) // 8, 'big')
        bytearray1.append(by.decode())
    return  "".join(bytearray1)
a=base64_decode(src)
print(a)