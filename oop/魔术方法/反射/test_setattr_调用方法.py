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
if hasattr(p1,"show"):
    getattr(p1,"show")()         # 调用show方法
