from pathlib import Path
p=Path("/a/b/c/d/mysql.tar.gz")


print(p.name)       # mysql.tar.gz
print(p.suffix)     #.gz
print(p.suffixes)   #['.tar', '.gz']
print(p.parts)
print(p.stem)
print(p.with_name("xiaoming"))  # 把 最后的/ 的后面 替换
print(p.with_suffix(".xiaoming"))      #把 后缀名  .gz替换掉

p= Path("readMe")
print(p.with_suffix(".text"))       #必须 . 结尾 否则会报错