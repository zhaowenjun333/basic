#!/usr/bin/env python3.6
# coding: utf-8
#testrandom.PY
# Created on 2017/12/23
# @author: zhaoyun
"""
description:

"""
import  random
#1
print(random.randint(0,10))
#2
print(random.randrange(0,10))

#3
dicta = dict.fromkeys(range(10),5)
print(dicta.keys())
lista = list(dicta.keys())
print(random.shuffle(lista))
print(lista)