#!/usr/bin/env python3.6
# coding: utf-8
#list_常用api.PY
# Created on 2017/12/24
# @author: zhaoyun
"""
description:

"""
lista = list(enumerate(list(range(10))))
print(lista )

print(lista.index((0,0)))
print(lista.index((1,1)))
print(lista[5])
lista.reverse()
print(lista[0])
print(lista )
def preanumbber(a ):
    if a ==(0,0):
        return  "a"
    if a ==(1,1):
        return "A"
lista.sort(preanumbber)
print(lista )

