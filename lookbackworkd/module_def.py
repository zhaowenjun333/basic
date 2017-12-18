#!/usr/bin/env python3.6
# coding: utf-8
#module_def.PY
# Created on 2017/12/13
# @author: zhaoyun
"""
description:

"""
import builtins
class A:
    def helloo(self):
        print("helllooo")
def hello():
    print("hell")
def hai(y,x=3):
    ...
#不能这样定义
# def hai(x=3,y):    和传参的一样
#     ...
hai(2,3)
hai(2,x=3)
flag= isinstance(1,int)
print(flag)
flag= isinstance(A(),A)      # obj,   class
print(flag)
lista =[1,2,4,2,2]
print(lista.count(2))
print(lista.reverse())
print(lista)
lista.sort()
print(lista)