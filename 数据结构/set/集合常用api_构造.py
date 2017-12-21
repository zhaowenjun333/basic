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
seta = set()     # 这种构造方法只需要满足可迭代即可
seta = set([1,])
seta = set(set((2,3,5)))
seta.update(range(3))     # 就地修改
print(seta)

a =(1,5,7,8,8)
seta.update(a)     # 就地修改
print(seta)
