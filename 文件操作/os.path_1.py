from os import path
# p=path.join(r"C:\Users\zhaoyun\PycharmProjects","basic")#C:\\Users\\zhaoyun\\PycharmProjects    三种写法都是可以的
# p=path.join("c:\\Users\\zhaoyun\\PycharmProjects","basic")#C:\\Users\\zhaoyun\\PycharmProjects  推荐使用 'c:/User/zhaoyun'   正斜线也是linux正统
p=path.join("c:/Users/zhaoyun/PycharmProjects","basic")#C:\\Users\\zhaoyun\\PycharmProjects  推荐使用 r'c:/User/zhaoyun'
print("返回一个字符串,然后字符串作为各个防范的参数")
print(p,'\n',type(p))
print(path.exists(p))
print(path.split(p))    # heead and tail
print(path.abspath("."))

print(path.dirname(p))
print(path.basename(p))

#驱动器名称  将  字符串拆分为一个元祖的两个元素   split  分离 分割
print(path.splitdrive(p))
