#!/usr/bin/env python3.6
# coding: utf-8
#字典to对象.PY
# Created on 2017/12/27
# @author: zhaoyun
"""
description:

"""
class Dict2Obj:
    def __init__(self,d:dict):
        self.__dict__["_dict"]=d

    def __getattr__(self, item):
        try:
            return self._dict[item]
        except KeyError:
            raise AttributeError("Attribute {} not found".format(item))
    def __setattr__(self, key, value):
        raise NotImplementedError

d ={"a":1,"b":2}
do =Dict2Obj(d)
print(do.a)
print(do.__dict__)
# print(do.c)