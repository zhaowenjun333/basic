#!/usr/bin/env python3.6
# coding: utf-8
#集合常用api.PY
# Created on 2017/12/21
# @author: zhaoyun
"""
description:
update   : 就地修改一个集合 参数是一个 元素      可以hash的   通俗的说就是不可变的  字符串,常量,元祖    不能是   列表, 字典,集合
set(iterable)

"""
seta = {1}      # 这种构造方法 需要满足里面的元素是    可以hash的   通俗的说就是不可变的  字符串,常量,元祖
print(seta)

seta.add(4)
print(seta)
seta.add((3,))
seta.update( "的风格的回复")     # 就地修改
print(seta)
print(seta.pop())   # 随机的抛出一个元素  就地修改
print(seta)