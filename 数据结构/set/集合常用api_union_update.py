#!/usr/bin/env python3.6
# coding: utf-8
#集合常用api.PY
# Created on 2017/12/21
# @author: zhaoyun
"""
description:
 union           返回一个新的集合
 和
 update 是一对   就地修改
"""
seta = {1,9,99}      # 这种构造方法 需要满足里面的元素是    可以hash的   通俗的说就是不可变的  字符串,常量,元祖
print(seta)
setb={0,False,"132",1,1}
print(seta.union(setb))   # 返回一个新的 集合
setc=set(range(10))
seta.update(setc)
print(seta)
