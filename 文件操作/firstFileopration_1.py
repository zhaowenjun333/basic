f=open(file="笔记.txt",encoding="utf-8")
print(f.read())
print("我还会再次读吗",f.read())
f.close()