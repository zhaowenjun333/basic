f=open(file="笔记copy3.txt",encoding="utf-8",mode="a+")    # 可以读写, 写到最后
print(f.tell())
print(f.read())   #
f.seek(0)
f.write("________________")
f.seek(0)
print(f.read(1))
f.write("+++++++++++")
f.close()
