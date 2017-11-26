#!/usr/bin/env python3.6
# coding: utf-8
#test__bool__.PY
# Created on 2017/11/24
# @author: zhaoyun
class A :
    pass

print(bool(A))
print(bool(A()))

class B:
    def __bool__(self):
        return False

print(bool(B))
print(bool(B()))

type