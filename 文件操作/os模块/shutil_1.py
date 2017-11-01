import shutil,os
with open(r'C:\Users\zhaoyun\PycharmProjects\basic\文件操作\a.txt','r+') as f1 :
    f1.write('abcdefg\n234')
    f1.flush()
    f1.seek(0)
    # f1.read()
    with open(r'C:\Users\zhaoyun\PycharmProjects\basic\文件操作\b.txt','w+') as f2:
        shutil.copyfileobj(f1,f2)      # 源码里面是  src.read 所以 需要将文件的指针  移到第一个位置
        print(os.stat(r'C:\Users\zhaoyun\PycharmProjects\basic\文件操作\a.txt'))
        print(os.stat(r'C:\Users\zhaoyun\PycharmProjects\basic\文件操作\b.txt'))

shutil.copymode(r'C:\Users\zhaoyun\PycharmProjects\basic\文件操作\a.txt',r'C:\Users\zhaoyun\PycharmProjects\basic\文件操作\b.txt')

print(os.stat(r'C:\Users\zhaoyun\PycharmProjects\basic\文件操作\a.txt'))
print(os.stat(r'C:\Users\zhaoyun\PycharmProjects\basic\文件操作\b.txt'))


shutil.copystat(r'C:\Users\zhaoyun\PycharmProjects\basic\文件操作\a.txt',r'C:\Users\zhaoyun\PycharmProjects\basic\文件操作\b.txt')

print(os.stat(r'C:\Users\zhaoyun\PycharmProjects\basic\文件操作\a.txt'))
print(os.stat(r'C:\Users\zhaoyun\PycharmProjects\basic\文件操作\b.txt'))