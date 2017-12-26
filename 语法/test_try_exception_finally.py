#!/usr/bin/env python3.6
# coding: utf-8
#test_try_exception_finally.PY
# Created on 2017/12/22
# @author: zhaoyun
"""
description:
在返回之前,会执行finally 里面的函数语句块
"""
def testme():
    try:
        raise Exception
    except:
        print(234)
        return "fuckboy"
    finally:
        print("PPPP")

if __name__=="__main__":
    a = testme()
    print(a )