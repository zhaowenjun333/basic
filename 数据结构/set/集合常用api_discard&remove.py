#!/usr/bin/env python3.6
# coding: utf-8
#集合常用api.PY
# Created on 2017/12/21
# @author: zhaoyun
"""
description:
"""
seta = {1}      # 这种构造方法 需要满足里面的元素是    可以hash的   通俗的说就是不可变的  字符串,常量,元祖
print(seta)

seta.update("gsdhshsjdj")
seta.add("aaaaaahs")
print(seta)
seta.discard(  "d")   #element 不存在 不会抛异常
print(seta)
seta.remove("g")      # key 不存在就会报错
print(seta)


def add(self, *args, **kwargs):  # real signature unknown
     pass


def clear(self, *args, **kwargs):  # real signature unknown

    pass


def copy(self, *args, **kwargs):  # real signature unknown

    pass


def difference(self, *args, **kwargs):  # real signature unknown
     pass


def difference_update(self, *args, **kwargs):  # real signature unknown

    pass


def discard(self, *args, **kwargs):  # real signature unknown
     pass


def intersection(self, *args, **kwargs):  # real signature unknown
     pass


def intersection_update(self, *args, **kwargs):  # real signature unknown

    pass


def isdisjoint(self, *args, **kwargs):  # real signature unknown

    pass


def issubset(self, *args, **kwargs):  # real signature unknown

    pass


def issuperset(self, *args, **kwargs):  # real signature unknown

    pass


def pop(self, *args, **kwargs):  # real signature unknown
     pass


def remove(self, *args, **kwargs):  # real signature unknown
     pass


def symmetric_difference(self, *args, **kwargs):  # real signature unknown
     pass


def symmetric_difference_update(self, *args, **kwargs):  # real signature unknown

    pass


def union(self, *args, **kwargs):  # real signature unknown
     pass


def update(self, *args, **kwargs):  # real signature unknown

    pass