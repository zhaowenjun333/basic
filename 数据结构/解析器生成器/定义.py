#!/usr/bin/env python3.6
# coding: utf-8
#定义.PY
# Created on 2017/12/30
# @author: zhaoyun
"""
description:生成器是迭代器,
迭代器是   可以被next()函数调用并不断返回下一个值的对象称为迭代器(Iterator)。



生成器可以通过两个方法得到
    1 通过生成器表达式得到生成器
    2 通过调用生成器函数得到生成器
"""

objofgenerator = (i for i in range(10))
print(type(objofgenerator))

def add ():
    yield  from [i for i in range(10)]

objtwo = add()
print(objtwo)