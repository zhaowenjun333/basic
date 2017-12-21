#!/usr/bin/env python3.6
# coding: utf-8
#集合常用api.PY
# Created on 2017/12/21
# @author: zhaoyun
"""
description:
update   : 就地修改一个集合 参数是一个可迭代对象
set(iterable)

"""
seta = {1}      # 这种构造方法 需要满足里面的元素是    可以hash的   通俗的说就是不可变的  字符串,常量,元祖
print(seta)


seta = {1,[2]}      # 这种构造方法 需要满足里面的元素是    可以hash的   通俗的说就是不可变的  字符串,常量,元祖
# seta = {1,{2}}      # 这种构造方法 需要满足里面的元素是    可以hash的   通俗的说就是不可变的  字符串,常量,元祖
print(seta)
