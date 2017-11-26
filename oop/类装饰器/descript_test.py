#!/usr/bin/env python3.6
# coding: utf-8
#descript_test.PY
# Created on 2017/11/17
# @author: zhaoyun
class A:
    def __init__(self):
        self.val="A.__init__value"

    def __get__(self, instance, owner):
        print("a.__get__")
        return self

class B:
    x =A()
    def __init__(self):
        self.x =A()
        print("B.__init__")


print(B.x.val)
print(B().x.val)