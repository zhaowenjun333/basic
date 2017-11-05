from collections import defaultdict

#
g=defaultdict(lambda x:0)
value=g.get("e")
print(value)

def fn():
    return 0
g=defaultdict(lambda :0)          # 顾名思义,就是给字典一个默认值
value1=g["e"]                           #如果没有默认值,那么会报错
print(value1)
