#!/usr/bin/env python3.6
# coding: utf-8
#fzhong.PY
# Created on 2018/1/7
# @author: zhaoyun
"""
description:

"""
class WrapperList(object):
    def __init__(self, values=None):
        self.values = values or []    #
    def __setitem__(self, key, value):
        self.values[key] = value
    def __getitem__(self, key):
        return self.values[key]


print(WrapperList(23).values)
print(WrapperList().values)