#!/usr/bin/env python3.6
# coding: utf-8
#bytes_定义.PY
# Created on 2017/12/26
# @author: zhaoyun
"""
description:
        bytes(iterable_of_ints) -> bytes
        bytes(string, encoding[, errors]) -> bytes
        bytes(bytes_or_buffer) -> immutable copy of bytes_or_buffer
        bytes(int) -> bytes object of size given by the parameter initialized with null bytes
        bytes() -> empty bytes object
"""
a =bytes([1,2,3,4,5])
print(a )

a = bytes("hello",encoding="utf-8")
print(a)

b = bytes(a)
print(b)