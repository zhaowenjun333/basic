sql= """select * from USER  where  uanme = 'zilong'"""
print(sql[4])        # 字符串访问 可以使用下标
print(sql[-3])      # 别忘了 还有单引号


for i in  sql:
    print(i)
    print(type(i))    # 类型是字符串 ,单个字符串


for   j  in  "abcdefghijklmnopqrstuvwxyz":        # 字符串是可以迭代的
    print(j)


print("\'".join("abcdefghijklmnopqrstuvwxyz"))      #      join 的用法:    在字符串中间加入   join 表示加入
alist = ["1","2","3"]                                #   无论join 什么 可迭代的对象 返回的都是字符串
print("%^&*(&&".join(alist))
print(type("%%%%%%".join(alist)))

#blist=['1',['2','3'],'4']          # 引用类型的数据类型是 不可以迭代的,所以是无法 被join的
#print(" ".join(blist))

print("we".find("e"))