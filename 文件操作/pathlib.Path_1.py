from pathlib import Path
p=Path('/a/b/c/d')   #当前的目录
print(p)
print(type(p))
print(p.parent)
print(p.parents)
for x in p.parents:
    print(x)
    print(type(x))
