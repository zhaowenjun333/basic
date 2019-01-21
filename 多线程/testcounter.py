#!/usr/bin/env python3.6
# coding: utf-8
#testcounter.PY
# Created on 2017/12/16
# @author: zhaoyun
"""
description:

"""
class Counter:
    def __init__(self):
        self.num=0
    def add(self):
        self.num+=1
    def minus(self):
        self.num-=1


if __name__=="__main__":
    c = Counter()
    c.add()
    print(c.num)