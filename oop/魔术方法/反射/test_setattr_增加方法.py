#!/usr/bin/env python3.6
# coding: utf-8
#test_setattr.PY
# Created on 2017/11/26
# @author: zhaoyun
#动态改变属性
class Point:
    def __init__(self,x,y):
        self.x = x
        self.y = y

    def __str__(self):
        return "Point({},{})".format(self.x,self.y)

    def show(self):
        print(self)   # 本质调用__str__()

p1 = Point(3,4)
p2 = Point(4,5)
if not  hasattr(Point,"add"):
    setattr(Point,"add",lambda  self,other:Point(self.x+other.x,self.y+other.y))     # 调用show方法

print(Point.show)  #<function Point.show at 0x056946F0>
print(Point.add)   #<function <lambda> at 0x0390D660>
print(p1.add)      #<bound method <lambda> of <__main__.Point object at 0x05692530>>
print(p1.add(p2))  #7,9