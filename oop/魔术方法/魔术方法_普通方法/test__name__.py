#!/usr/bin/env python3.6
# coding: utf-8
#test__name__.PY
# Created on 2017/11/21
# @author: zhaoyun
class A:
    pass
class B(A):
    """
    this is test property
    """
    X=12
    pass
    def __dir__(self):
        return ["this","is","dirreload"]

print(B.__name__)               #实例没有这个属性
print(B.__class__ )         #实例有这个方法 返回值:<class '__main__.B'>
print(B.__doc__)
print(B.__dict__)
print(B.__module__)
print(B.__bases__)       # 返回值是一个元祖
print(B.mro())              #返回一个列表
print(B.__mro__)          # 返回一个元祖
print(dir(B()))
print(dir(B))
print(dir())