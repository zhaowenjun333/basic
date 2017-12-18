#!/usr/bin/env python3.6
# coding: utf-8
#implementTimer.PY
# Created on 2017/12/14
# @author: zhaoyun
"""
description:

"""
import threading

def add(x,y):
    print(x+y)

t= threading.Timer(2,add,args=(4,5))
t.start()