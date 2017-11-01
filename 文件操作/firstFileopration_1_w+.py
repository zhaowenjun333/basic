f=open(file="笔记copy3.txt",encoding="utf-8",mode="w+")    # 可以读写,
print(f.read())   #清空 后打开
f.seek(0)
f.write("zhidaome二五眼而与")
f.seek(0)
print(f.read())
f.closed
f.close()
