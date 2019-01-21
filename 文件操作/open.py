
with open('test',encoding="utf-8",mode='r+') as f :

    f.write("啊111")
    f.seek(0)
    print(f.read(1))
    # print(f.read(1))
    # print(f.read(1))
    f.write("和那后")
    print(f.read(1))
    print(f.read(1))



