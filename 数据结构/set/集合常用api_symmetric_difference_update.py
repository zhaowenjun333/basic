#!/usr/bin/env python3.6
# coding: utf-8
#集合常用api.PY
# Created on 2017/12/21
# @author: zhaoyun
"""
description:
 对称差分  返回集合不重复的
"""
seta = {1,9,99}      # 这种构造方法 需要满足里面的元素是    可以hash的   通俗的说就是不可变的  字符串,常量,元祖
print(seta)

setc=set(range(10))
print(seta.symmetric_difference(setc))   #  返回一个新的 集合  对称差分
print(seta)
seta.symmetric_difference_update({99})    # 就地修改
print(seta)
