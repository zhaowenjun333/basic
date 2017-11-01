
# f=open(r'C:\Users\zhaoyun4240\PycharmProjects\basic\文件操作\笔记.txt',encoding="utf_8")
f=open(r'笔记.txt',encoding="utf_8",mode="r")
# f=open(r'C:\Users\zhaoyun4240\win10系统备份\python课件文档\answer\1.py')
# print(f.read())       # 只可以读取一次
temp=f.read()
f1=open(r'笔记copy.txt',encoding="utf_8",mode="w")
f1.write(temp)
f.close()
f1.close()
