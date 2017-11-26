#!/usr/bin/env python3.6
# coding: utf-8
#TestMro.PY
# Created on 2017/11/17
# @author: zhaoyun

class Animal:
    pass


class Dog(Animal):
    pass

print(Dog.mro())
print(dir(Dog()))
print(Dog.__mro__)
print(Dog.__bases__)