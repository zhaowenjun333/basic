#!/usr/bin/env python3.6
# coding: utf-8
#context_伪装.PY
# Created on 2017/11/17
# @author: zhaoyun

#contextlib.contextmanager是一个装饰器实现上下文管理       ,用来装饰一个函数 且这个函数必须是一个生成器
import contextlib

@contextlib.contextmanager
def foo():
    print("enter")
    try:
        yield  3,5
    finally:
        print("exit")
    # print("exit")


with foo() as f :
    # raise Exception                    # 增加异常 也不会出问题,因为有try   exception   finally
    print(f )