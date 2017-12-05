#!/usr/bin/env python3.6
# coding: utf-8
#test_staticmethod.PY
# Created on 2017/11/27
# @author: zhaoyun
"""
description:

"""
class Dog:
    @classmethod
    def foo(cls):
        pass
    @staticmethod
    def bar():
        pass
    @property    #name =property(name)
    def name(self):
        return "lili"
    @name.setter       # name = name.setter(name)(self, item)         #setter 方法返回name->fn
    def name(self,item):
        self.a=item
    def getfoo(self):
        return self.foo
    def __init__(self):
        self.name="bobo"
        self.foo=100
        self.bar=200

a = Dog()
print(a.__dict__)
print(Dog.__dict__)


