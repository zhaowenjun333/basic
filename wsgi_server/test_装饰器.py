#!/usr/bin/env python3.6
# coding: utf-8
#test_装饰器.PY
# Created on 2017/12/28
# @author: zhaoyun
"""
description:

"""
x = 1
y =2
class A :
    def add(self,fun):
        def wrapper(*args):
            global x
            global y
            y =   x +2
            return fun(*args)
        self.a =y
        return wrapper
a = A()
@a.add    #app = a.add(app)
def app(a,b):
    return a+b

# print(app(4,5))
print(y )
print(a.a)


