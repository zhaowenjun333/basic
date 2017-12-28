#!/usr/bin/env python3.6
# coding: utf-8
# 字典to对象.PY
# Created on 2017/12/27
# @author: zhaoyun
"""
description:
字典的意义是存储属性
"""


class Person:
    def __init__(self):
        self.name="zhaoyun"
        self.age="21"


p = Person()
p.name
p.age
print(p.__dict__)
