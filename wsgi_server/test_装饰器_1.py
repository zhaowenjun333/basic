#!/usr/bin/env python3.6
# coding: utf-8
# test_装饰器.PY
# Created on 2017/12/28
# @author: zhaoyun
"""
description:
看似没有调用装饰器,因为getone函数已经调用了


"""


class A:


    def getone(self):   # getone(self)(app)   ->self.add(1)(app)
        return self.add(1)

    def add(self, num):
        def wrapper(fun):
            self.a = num
            return fun
        return wrapper


a = A()


@a.getone()  # app = a.getone(app)  ->app =a.add(1)(app)  ->app =app  ->跑了一圈啥鸡吧也没变,就为了给实例增加了属性
def app(c, b):
    return c + b


print(app(4,5))
print(a.a)
