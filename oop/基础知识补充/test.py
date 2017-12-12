#!/usr/bin/env python3.6
# coding: utf-8
#test.PY
# Created on 2017/12/11
# @author: zhaoyun
"""
description:  方向方法    add  iadd radd   1+a 会调用 A类的魔术方法__radd__()

"""
class A:
    def __init__(self,x):
        self.x=x
    # def __add__(self, other):
    #     return self.x + (other.x if type(other)==A else other)  # 可以用 try catch

    def __add__(self, other):
        try:
            return self.x +other.x
        except:
            other = int(other)
            return  self.x +other

    def __iadd__(self, other):
        pass
    def __radd__(self, other):
        return  self+other

a=A(2)
b=A(3)

print(a+b)
print(1+a)