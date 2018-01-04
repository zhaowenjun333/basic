#!/usr/bin/env python3.6
# coding: utf-8
#zip.PY
# Created on 2017/12/30
# @author: zhaoyun
"""
description:
zip(iter1 [,iter2 [...]]) --> zip object
"""

a = zip(range(10),dict.fromkeys(range(10)))
print(a )
for i in a:
    print(i)
print(dict.fromkeys(range(10)))   # 一个只有key的字典