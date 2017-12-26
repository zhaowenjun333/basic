#!/usr/bin/env python3.6
# coding: utf-8
#dict_构造.PY
# Created on 2017/12/21
# @author: zhaoyun
"""
description:
访问有三种:
0x00,下标:有序的,元祖,列表
0x01.get(k,d=None):
        1,key 有了匹配,返回value
        2.key没有匹配:  1,有默认值:返回默认值d= 默认
                    2没有默认值,返回d=None

0x02 setdefaul(key[,value]):
        1,key 有了匹配  返回value
        2,key 没有匹配  有默认值,设置value 值,返回默认值
                        没有默认值则设置为None
 """
dicta = dict.fromkeys(range(10), 5)  # 构造一个字典

print(dicta.get(23,4))    # 返回4
print(dicta.get(23 ))     # 返回None
print(dicta.get(3,4))

print(dicta.setdefault(2,1))
print(dicta.setdefault(22,1))
print(dicta)