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
        self.a1 = "ai"
        print("A.init")

class B:
    x = A()
    def __init__(self):
        self.a1="b.init"
        print("B.init")

print(B.x)
print(B.x.a1)
print(B().x.a1)
print(B.__dict__)
print(B().__dict__)