from os import path
import os
import sys
print(os.name)
# print(os.uname)   #linux 支持显示

print(sys.platform)
print(os.listdir(r'C:\Users\zhaoyun\PycharmProjects\basic\文件操作\a'))   #返回目录内容列表

print(os.stat(r'C:\Users\zhaoyun\PycharmProjects\basic\文件操作\a'))

