f=open(file="笔记copy3.txt",encoding='utf-8',mode="r+")    # 可以读写,从头开始写,会覆盖之前的字节数
print(f.tell())
f.write("十大")
print(f.read())
f.seek(0)
print(f.read())
f.close()
