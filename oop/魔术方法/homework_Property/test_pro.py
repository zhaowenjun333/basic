#!/usr/bin/env python3.6
# coding: utf-8
#test_pro.PY
# Created on 2017/11/26
# @author: zhaoyun
"""
description:

"""
class Property:
    def __init__(self):
        pass
    def __get__(self, instance, owner):
        pass
    def __set__(self, instance, value):
        pass

class A :
    def __init__(self,data):
        self._data= data

    @property
    def data(self):
        return self._data

    @data.setter
    def data(self,value):
        self._data=value
