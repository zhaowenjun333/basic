#!/usr/bin/env python3.6
# coding: utf-8
#module_def.PY
# Created on 2017/12/13
# @author: zhaoyun
"""
description:
1.导入的可以是可以模块,一个类,一个方法, 准确的说是一个dir 一个名字空间
2.from 一定是module
"""
import builtins
from lookbackworkd.module_def import hello
from lookbackworkd.module_def  import A
import threading ,logging
def hai():
    ...

def hai1():
    pass
print(callable(hai))
print(callable(hai1))

hello()
A().helloo()