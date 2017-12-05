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
    @property
    def name(self):
        return "lili"
    @name.setter    # name =name.setter(name)(self,value)
    def name(self,value):
        self._name = value
    def getfoo(self):
        return self.foo
    def __init__(self):
        self.name="bobo"
        self.foo=100
        self.bar=200

a = Dog()
print(a.foo)
print(a.bar)


