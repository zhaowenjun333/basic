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

    def show(self):
        print(self.x,self.y)


p =Point(4,5)
print(p)
print(p.__dict__)
p.__dict__['y']=16                 # 动态修改 对象的字典的数值
print(p.__dict__)
p.z =12                            #动态增加属性
print(p.__dict__)
print(">>>>>>>>>",sorted(dir(p)))
print("??????????",sorted(p.__dir__()))