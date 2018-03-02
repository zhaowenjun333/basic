#!/usr/bin/env python3.6
# coding: utf-8
#空格逗号分隔.PY
# Created on 2018/1/26
# @author: zhaoyun
"""
description:

"""

import re
pattern = "\w+"
re = re.compile(pattern)
str = "a b,c rrr d"
matcher = re.findall(str)

print(matcher)
import re
mat = re.split("[\s,]+",str)
print(matcher)

# ops={}
# mapa = {k:ops.get(k, lambda x:x)(v) for k,v in matcher.groupdict().items()}
# # mapa = {k:v for k,v in matcher.groupdict().items()}     # 两种写法是一样的
# print(mapa)