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
        self.y = "b.y"
        self.x ="ddd"
print("this is b.dict ",B().__dict__)                   #实例字典不是空说明了给实例增加了其他的属性 ,只有实例属性是x的时候才会 调用__set__
print("this is B.dict ",B.__dict__)                   #实例字典是空说明了,赋值并没有给dict 而是 __set__ 方法
print(B.x.a1)
b  =B()
print(b.x.a1)
print(b.y)
print(b.__dict__)