# f=open('test',encoding='utf-8',mode='r+')
# print(f.read())
# f.write("123")

# 啊这是什么鬼啊啊啊啊
with open('test',encoding="utf-8",mode='r+') as f :
    print(f.read(1))
    print(f.read(1))
    print(f.read(1))
    print(f.tell())
    f.write("啊")
    print(f.tell())
    print(f.tell())
    print(f.read(1))

    print(f.read(1))




