#!/usr/bin/env python3.6
# coding: utf-8
#内建函数__import__().PY
# Created on 2017/12/11
# @author: zhaoyun
"""
description:

"""
from 插件化开发  import   test1_plugin
class A:
    def showme(self):
        print("i am A ")


if __name__=="__main__":
    #1.mod 是一个模块名,利用方法 __import获取
    mod =__import__("内建函数__import__()")   #
    print(type(mod))
    print(mod)
    print(12)
    cls=getattr(mod,'A')
    cls().showme()

    #2.A 是一个类名
    print(getattr( A,'showme'))

    #3. test1_plugin 是一个模块名,利用 import 导入
    print(type(test1_plugin))
    print(getattr( test1_plugin,'A'))