#!/usr/bin/env python3.6
# coding: utf-8
#插件化编程核心代码.PY
# Created on 2017/12/11
# @author: zhaoyun
"""
description:

"""
import  importlib

class A:
    def showme(self):
        print("iam A ")

def plugin_load(plugin_name:str,sep=':'):
    m,_,c=plugin_name.partition(sep)
    mod=importlib.import_module(m)
    cls=getattr(mod,c)
    return cls()

if __name__=="__main__":
    a = plugin_load("test1_plugin:A")
    a.show()

