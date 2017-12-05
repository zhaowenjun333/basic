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
print(repr(p1),repr(p2),sep='\n')
print(p1.__dict__)
setattr(p1,"x",6)
setattr(p1,"y",8)
print(p1.__dict__)