#!/usr/bin/env python3.6
# coding: utf-8
# 字典to对象.PY
# Created on 2017/12/27
# @author: zhaoyun
"""
description:

"""


class Dict2Obj:
    def __init__(self, d: dict):
        self.__dict = d

    def __getattr__(self, item):
        return self.__dict[item]

    def __setattr__(self, key, value):
        pass


d = {"a": 1, "b": 2}
do = Dict2Obj(d)
print(do.a)
