#!/usr/bin/env python3.6
# coding: utf-8
#test__get__().PY
# Created on 2017/11/26
# @author: zhaoyun
class A :
    def __init__(self):
        print("A() is init")

class B :
    x = A()

    def __init__(self):
        print("B() is init")


# print(B.x)
print(B().x)
