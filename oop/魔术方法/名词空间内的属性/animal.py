#!/usr/bin/env python3.6
# coding: utf-8
#test_dir.PY
# Created on 2017/11/23
# @author: zhaoyun
import random
class A:
    pass
class Animal:
    X=20
    def __init__(self,name):
        self._age=10
        self._name=name
        self.weight=20
    def hell(self):
        pass
    @classmethod
    def getanumber(cls):
        return random.randint(10)
    def __dir__(self):                                   # 这也是一个魔术方法,可以注销后看对象的dir(object)
        return ["this","is","dirreload"]
print("Animal()对象的 Module's name ={}".format(dir(Animal("xiaomi"))))       #对象的属性
print("+++++++++++++++++++++++++++++++")
print("Animal Module's name ={}".format(dir(Animal)))       #类的属性
print("+++++++++++++++++++++++++++++++")
print("animal Module's name ={}".format(dir()))       #整个模块的 属性
print("+++++++++++++++++++++++++++++++")
print("Animal__dict__is:",Animal.__dict__)
print("+++++++++++++++++++++++++++++++")
print(object.__dict__)


