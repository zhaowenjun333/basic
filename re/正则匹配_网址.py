#!/usr/bin/env python3.6
# coding: utf-8
#正则匹配_网址.PY
# Created on 2017/12/26
# @author: zhaoyun
"""
description:

"""
import re
pattern = "(?P<first>\w+)://(?P<two>[\w.]*)(?P<three>:\d*)?(?P<four>[\w.\-/]*)"
re = re.compile(pattern)
str = "http://www.runoob.com:80/html/html-tutorial.html"
matcher = re.match(str)
print(matcher)
ops={}
mapa = {k:ops.get(k, lambda x:x)(v) for k,v in matcher.groupdict().items()}
# mapa = {k:v for k,v in matcher.groupdict().items()}     # 两种写法是一样的
print(mapa)