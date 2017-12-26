#!/usr/bin/env python3.6
# coding: utf-8
#dict_defaultdict.PY
# Created on 2017/12/23
# @author: zhaoyun
"""
description:

"""
import random,collections
dicta = dict.fromkeys(range(10),5)
print(dicta.keys())
lista = list(dicta.keys())
print(random.shuffle(lista))
print(lista)

orderedDict = collections.OrderedDict()
for k,v in enumerate(lista):
    orderedDict[k]=v
print(orderedDict)

for i in orderedDict.keys():
    print(orderedDict[i])
