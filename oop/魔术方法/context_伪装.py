#!/usr/bin/env python3.6
# coding: utf-8
#context_伪装.PY
# Created on 2017/11/17
# @author: zhaoyun


import contextlib

@contextlib.contextmanager
def foo():
    print("enter")
    try:
        yield  3,5
    finally:
        print("eit")
    # print("exit")


with foo() as f :
    # raise Exception
    print(f )