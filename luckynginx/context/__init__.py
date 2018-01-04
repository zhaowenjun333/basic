#!/usr/bin/env python3.6
# coding: utf-8
#__init__.py.PY
# Created on 2017/12/29
# @author: zhaoyun
"""
description:

"""

class Context(dict):
    def __getattr__(self, item):
        try:
            return self[item]
        except KeyError:
            raise AttributeError("Attribute {} not Found".format(item))

    def __setattr__(self, key, value):
        self[key] = value


class NestedContext(Context):
    def __init__(self, globalcontext: Context = None):
        super().__init__()
        self.relate(globalcontext)

    def relate(self, globalcontext: Context = None):
        self.global_context = globalcontext

    def __getattr__(self, item):
        if item in self.keys():
            return self[item]
        return self.global_context[item]


