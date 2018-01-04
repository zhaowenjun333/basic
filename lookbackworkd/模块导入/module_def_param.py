#!/usr/bin/env python3.6
# coding: utf-8
#module_def.PY
# Created on 2017/12/13
# @author: zhaoyun
"""
description:
1.from import导入的可以是可以模块,一个类,一个方法, 准确的说是一个dir 一个名字空间
2.from 一定是module,即模块  ->       文件.py
3.import 是module
"""
import lookbackworkd.排序算法.冒泡排序
from lookbackworkd.模块导入.module_def import hello
from lookbackworkd.模块导入.module_def  import A
import threading ,logging
def hai():
    ...

def hai1():
    pass
print(callable(hai))
print(callable(hai1))

hello()
A().helloo()
helloo()
