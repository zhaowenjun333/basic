#!/usr/bin/env python3.6
# coding: utf-8
#反射_魔术方法__getattr__().PY
# Created on 2017/11/26
# @author: zhaoyun
class Base:
    n=0
class Point(Base):
    z=6
    def __init__(self,x,y):
        self.x = x
        self.y = y

    def __getattr__(self, item):
        return "missing {}".format(item)
    def __getattribute__(self, item):
        return object.__getattribute__(self,item)

p1 = Point(5,6)
print(p1.x)
print(p1.z)
print(p1.n)
print(p1.t)    # 没有这个属性就会调用__getattr__()这个方法
