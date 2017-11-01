import shutil,os

strf1=r'C:\Users\zhaoyun\PycharmProjects\basic\文件操作\a.txt'
strf2=r'C:\Users\zhaoyun\PycharmProjects\basic\文件操作\b.txt'
shutil.copy(strf1,strf2)      #  这里面的参数是字符串
print(os.stat(r'C:\Users\zhaoyun\PycharmProjects\basic\文件操作\a.txt'))
print(os.stat(r'C:\Users\zhaoyun\PycharmProjects\basic\文件操作\b.txt'))