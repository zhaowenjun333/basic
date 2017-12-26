#!/usr/bin/env python3.6
# coding: utf-8
#dict_defaultdict.PY
# Created on 2017/12/23
# @author: zhaoyun
"""
description:

"""
from collections import defaultdict
d2 = defaultdict(list)    # 函数list
print(d2)
for k in 'mnopq':
    for v in range(3):
        d2[k].append(v)   # 是一个列表
print(d2)
print(type(d2))
for k in d2.items():
    print(k)
print("+++++++++++++++++++")
d1={}
for k in "abcde":
    for v in range(5):
        if k not in d1.keys():
            d1[k] = []
        d1[k].append(v)
print(d1)