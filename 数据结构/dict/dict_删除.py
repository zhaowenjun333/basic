#!/usr/bin/env python3.6
# coding: utf-8
#dict_构造.PY
# Created on 2017/12/21
# @author: zhaoyun
"""
description:
访问有三种:
0x00,pop(key[,default]):有序的,元祖,列表
         1,key 有了匹配,返回value
        2.key没有匹配:  1,有默认值:返回默认值d= 默认
                    2没有默认值,抛出异常

0x01. popitem()
         移除并返回一个任意的键值对

0x02   clear()
         清空一个字典
 """
dicta = dict.fromkeys(range(10),  5)  # 构造一个字典
print(dicta)
print(dicta.pop(3))
print(dicta.pop(33,4))
print(dicta)
print(dicta.popitem())    # 移除并返回一个任意的键值对
