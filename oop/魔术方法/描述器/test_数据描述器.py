#!/usr/bin/env python3.6
# coding: utf-8
#test_数据描述器.PY
# Created on 2017/11/26
# @author: zhaoyun
"""
description:  实现了__get__ 和 __set__  就会是一个数据描述器 ,查找顺序优先于  __dict__ ,假象 本质是从字典中去除了
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
class B:
    x = A()
    def __init__(self):
        print("B.init")
        self.x = "b.x"
print("this is bdict ",B().__dict__)                   #实例字典是空说明了,赋值并没有给dict 而是 __set__ 方法
print("this is Bdict ",B.__dict__)                   #实例字典是空说明了,赋值并没有给dict 而是 __set__ 方法
# print(B.x.a1)
# print(B().x.a2)
b  =B()
print(b.x.a1)
print(b.__dict__)