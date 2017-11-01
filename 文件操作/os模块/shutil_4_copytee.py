"""
def copytree(src, dst, symlinks=False, ignore=None, copy_function=copy2,
             ignore_dangling_symlinks=False):
 src->Recursively copy a directory tree.   src和dst都是目录

"""
import shutil,os

def ignore1(src,names):
    ig=filter(lambda x: not x.startswith("a"),names)
    return set(ig)
strf1=r'C:\Users\zhaoyun\PycharmProjects\basic\文件操作\a'
strf2=r'C:\Users\zhaoyun\PycharmProjects\basic\文件操作\b'
shutil.copytree(strf1,strf2,ignore=ignore1)      #  这里面的参数是字符串
print(os.stat(r'C:\Users\zhaoyun\PycharmProjects\basic\文件操作\a'))
print(os.stat(r'C:\Users\zhaoyun\PycharmProjects\basic\文件操作\b'))