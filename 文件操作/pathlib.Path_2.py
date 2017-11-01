from pathlib import Path
p=Path()   #当前的目录
print(p.absolute())
p=p / 'c:/Users/zhaoyun'      # 有三种写法    建议写这种
print(p.exists())
print(p.absolute())
print(p)

print("++++++++++++++++有参构造函数++++ 会自己添加/+++++++++++++")
p=Path("c:/",'Users')
print(p)

print("+++++++++++++++/号运算符 会自己添加 /  ,但是如果遇到字符串开头有/ 则 认为从/ 开始 ++++++++++++")
p=Path('a','c')   #d
print(p.absolute())   # -> C:\Users\zhaoyun\PycharmProjects\basic\文件操作\a
p /= "a/b"  #  -> /b    如果是"b",则  ->a/b
print(p)
print(p.absolute())
print("part方法将p对象分割为一个元祖")
print(p.parts)
p =p.joinpath("/etc",'init.d')     #和构造参数一样,自动拼接 /   ,如果有 /开头的 就  从根拼接
print(p)