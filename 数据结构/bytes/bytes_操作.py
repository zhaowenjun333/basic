#!/usr/bin/env python3.6
# coding: utf-8
#bytes_定义.PY
# Created on 2017/12/26
# @author: zhaoyun
"""
description:
String 必须是两个字符的16进制表示形式
"""
a = bytes.fromhex("6162")
a = bytes.fromhex("01")
print(a)

print("a".encode().hex())   # bytes.hex()->str    bytes.decode()->str
print(type("a".encode().hex()))