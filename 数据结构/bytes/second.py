# 使用的地方,两个js页面跨域  请求相互传输数据,需要 传输字节码,  所以需要把  字符串  转化位字节码  然后传输 ,
# 到另外一个域 需要解码 , decoding


stra = r"""{'name': 'xiaoming'
wodh\tim
klkwl
kls}"""
# a =stra.encode(encoding='utf-8')
a =stra.encode()            # 编码
print(a)        # 转化为字节码     方便传输 ,不会因为传输问题导致 乱码

print(a.decode())     #   解码    解码后 的 类型是str
print(type(a.decode()))
print(bytes("hhahha",'utf8'))       # 编码  使用bytes() 方法 编码,同 "hhahha".encode()

cc = b'123456'
dd = bytes(cc)
print(dd)

ee =bytes(range(33,35)).decode()
print(ee)


ff= bytes([7,3])
print(ff)




