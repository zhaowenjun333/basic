from pathlib import Path
p=Path("a/b/c/d/mysql.tar.gz")
print(p.absolute())
print(p.exists())
print(p.cwd())   # 当前工作 目录
print(p.home())   # 返回当前家目录

print(p.is_socket())
# p.touch(exist_ok=True)
print(p.resolve())   #
p=Path('a/a.txt')
p.mkdir(parents=True)
p.rmdir()     # 目录需要是空的才可以
print(p.parent)