#!/usr/bin/env python3.6
# coding: utf-8
#firstdeml.PY
# Created on 2018/1/6
# @author: zhaoyun
"""
description:
使用 类类型的类type去管理所有的类 ,初始化type就可以生成一个类
"""
xclass = type("x",(object,),{})

print(xclass)
print(xclass.__dict__)
print(xclass.mro())
print(type(xclass))

