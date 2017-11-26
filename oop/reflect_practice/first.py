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
if not hasattr(Point,"add"):
    setattr(Point,"add",lambda self,other:print(self.x+other.x,self.y+other.y))
print(p.add)
print(Point.add)
p.add(Point(2,2))
print("this is instancedict",p.__dict__)
print("this is objectdict",Point.__dict__)

