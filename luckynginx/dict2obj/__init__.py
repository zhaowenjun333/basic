#!/usr/bin/env python3.6
# coding: utf-8
#__init__.py.PY
# Created on 2017/12/29
# @author: zhaoyun
"""
description:

"""
class Dict2Obj:
    def __init__(self, d: dict):
        self.__dict__["_dict"] = d

    def __getattr__(self, item):
        try:
            return self._dict[item]
        except KeyError:
            raise AttributeError("Attribute {} not found".format(item))

    def __setattr__(self, key, value):
        raise NotImplementedError

