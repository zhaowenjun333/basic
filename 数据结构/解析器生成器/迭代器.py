#!/usr/bin/env python3.6
# coding: utf-8
#迭代器.PY
# Created on 2017/12/30
# @author: zhaoyun
"""
description:
迭代器:可以被next()函数调用并不断返回下一个值的对象称为迭代器(Iterator)。
"""
obj_of_iter= iter(dict.fromkeys(range(10), 5))
print(type(obj_of_iter))

while True:
    try:
        print(next(obj_of_iter))
    except:
        break