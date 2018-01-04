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
print('123453235432345654323456765432345678765432123678')
#4
dicta = random.choice([i for i in range(10)])
print(dicta)
