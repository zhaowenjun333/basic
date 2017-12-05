#!/usr/bin/env python3.6
# coding: utf-8
#test_数据描述器.PY
# Created on 2017/11/26
# @author: zhaoyun
"""
description:  实现了__get__ 和 __set__  就会是一个数据描述器 ,查找顺序优先于  __dict__
"""
class A :
    def __init__(self):
        self.a1 = "ai"
        print("A.init")
    def __get__(self, instance, owner):
        print("A.__get__{}{}{}".format(self,instance,owner))
        return self
    def __set__(self, instance, value):
        print("A.__set__{}{}{}".format(self,instance,value))
        # self.a1=value
        self.a2=value
    def __repr__(self):
        return "A.repr"
class B:
    x = A()
    def __init__(self):
        print("B.init")
        self.y = "b.x"


print("_"*10, B.__dict__ )
B.x           # 访问
B.x = 200   #赋值
print(B.x)
print("+"*10, B.__dict__)
