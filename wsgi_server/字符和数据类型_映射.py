#!/usr/bin/env python3.6
# coding: utf-8
# 字符和数据类型_映射.PY
# Created on 2017/12/28
# @author: zhaoyun
"""
description:

"""
import re

pattern = re.compile("/({[^{}:]+:?[^{}:]*})")
s = "/student/{name:str}/xxx/{id:int}/"
TYPECAST = {
    "str": str,
    "word": str,
    "int": int,
    "float": float,
    "ant": str
}

TYPE_PATTERN = {
    "str": r"[^/]+",
    "word": r"\w+",
    "int": r"[+-]?\d+",
    "float": r"[+-]?\d+\.\d+",
    "and": r".+"
}


def transform(kv: str):
    name, type_param = kv.strip("/{}").split(":")
    return "/(?P<{}>{})".format(name, TYPE_PATTERN.get(type_param, "\w+")), name, TYPECAST.get(type_param, str)


def parse(src: str):
    start = 0
    res = ""
    translator = {}
    while True:
        matcher = pattern.search(src, start)
        if matcher:
            res += matcher.string[start:matcher.start()]
            tmp = transform(matcher.string[matcher.start():matcher.end()])
            # <class 'tuple'>: ('/(?<name>[^/]+)', 'name', <class 'str'>)
            res += tmp[0]
            translator[tmp[1]] = tmp[2]
            start = matcher.end()
        else:
            break
    if res:
        return res, translator, "res"
    else:
        return src, translator


print(parse(s))
#  ('/student/(?<name>[^/]+)/xxx/(?<id>[+-]?\\d+)', {'name': <class 'str'>, 'id': <class 'int'>})
#                                                             class名字              class名字
