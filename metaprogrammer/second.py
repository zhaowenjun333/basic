#!/usr/bin/env python3.6
# coding: utf-8
#second.PY
# Created on 2018/1/6
# @author: zhaoyun
"""
description:

"""


class Xmeta(type):
    def __new__(cls,*args, **kwargs):
        print("3333")
        t = super()
        t.__new__(cls,*args, **kwargs)


class A (metaclass=Xmeta):
    pass