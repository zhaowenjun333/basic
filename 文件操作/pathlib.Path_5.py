from pathlib import Path

p=Path('a/b/c')   # Path('.')
for x in p.parents:
    print(x)
    print(type(x))
print("+"*50)
print(p.parent)
for i in p.parents[len(p.parents)-1].iterdir():#等价于 Path('.') ,  是当前的并不是c盘符才是根目录.
    ## parents 是一个 列表 , 最后一层是盘符 c:/Users/zhaoyun/
    print(i.name)


