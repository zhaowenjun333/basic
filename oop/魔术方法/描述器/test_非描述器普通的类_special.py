#!/usr/bin/env python3.6
# coding: utf-8
#test_数据描述器.PY
# Created on 2017/11/26
# @author: zhaoyun
"""
description:

"""
class A :
    def __init__(self):
        self.a = "bingo"
        print("A.init")
    def __get__(self, instance, owner):
        print("A.get:{},{},{}".format(self,instance,owner))
        return self   # 这一步很关键,没有返回self, B.x 就是None
class B:
    def __init__(self):
        print("B.init")
        self.x = A()
print(B.__dict__)
# print(B.x)
print(B().__dict__)
print(B().x)