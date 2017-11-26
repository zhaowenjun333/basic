#!/usr/bin/env python3.6
# coding: utf-8
#first.PY
# Created on 2017/11/17
# @author: zhaoyun
class Point:
    def __init__(self,x,y):
        self.x=x
        self.y = y

    def __str__(self):
        return "{},{}".format(self.x,self.y)
    def __repr__(self):
        return "repr is {},{}".format(self.x,self.y)
    def show(self):
        print(self.x,self.y)


p =Point(4,5)
print(p)
print(getattr(p,"x",39))              # 内建函数 获取对象实例的属性 ,  getattr(object,name[,default])
print(getattr(p,"y",39))

print(hasattr(p,"z"))
print(hasattr(p,"x"))

p1=Point(1,2)
print(repr(p),repr(p1),"testprintsep",sep="\n")        # 打印换行