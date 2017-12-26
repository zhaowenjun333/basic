#!/usr/bin/env python3.6
# coding: utf-8
#dict_构造.PY
# Created on 2017/12/21
# @author: zhaoyun
"""
description:
key :必须是可hash ,和set元素要求一致

dict(iterable) -> new dictionary initialized as if via:
            d = {}
            for k, v in iterable:
                d[k] = v
"""
# 一共4种 构造器
#1:key value
a = "ee"
dicta = {a:"th"}
dicta = dict(one=1, two=2)
print(dicta)

dicta = {1:3}
print(dicta)

# 2:dict(iterable)
#  构造器里面是一个多元元祖(可迭代)  for k, v in iterable     iterable :(("e",5),("4",9),(3,3))
dicta = dict()
print(dicta)

dicta = dict((("e",5),("4",9),(3,3)) )  #  构造器里面是一个多元元祖  for k, v in iterable     iterable :(("e",5),("4",9),(3,3))
dicta = dict([("e",0),("4",9),(3,3)] )  #  构造器里面是一个多元元祖  for k, v in iterable     iterable :(("e",5),("4",9),(3,3))
dicta = dict({("e",0),("4",9),(3,3)} )  #  构造器里面是一个多元元祖  for k, v in iterable     iterable :(("e",5),("4",9),(3,3))

print( dict(enumerate(range(10)))) # enumerate(range(10))   是这样的((0,0),(1,1),(2,2),(3,3,),(4,,4),(5,5))
print(dicta)

3,# dict(one=1, two=2)    使用
dicta = dict(one=1, two=2)
print(dicta)

#4 dict(mapping)
dicta = dict(dict(one=1, two=2))      # 用字典生成一个新字典
print(dicta)

#5  静态方法    完全独立的几乎和dict类没有逻辑关系 ,业务上属于她管 from iterable

dicta = dict.fromkeys(range(10),5)
print(dicta)

dicta = dict.fromkeys(range(10) )
print(dicta)

#6 copy()  # shadow copy
dictb = dicta.copy()
print(dictb)