#!/usr/bin/env python3.6
# coding: utf-8
#证明一个汉字占几个字节.PY
# Created on 2017/12/26
# @author: zhaoyun
"""
description:
一共十个 字节 其中空格是一个字节,  汉字三个,均分9个字节
"""
print("我爱 你".encode(encoding="utf-8").hex())
a="我爱 你".encode(encoding="utf-8")
print(a)
print(len(a))


print(a.decode(encoding="utf-8"))

print("nihao".encode(encoding="utf-8").hex())
print("nihao".encode(encoding="utf-8") )
