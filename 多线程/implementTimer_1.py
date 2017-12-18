#!/usr/bin/env python3.6
# coding: utf-8
#implementTimer.PY
# Created on 2017/12/14
# @author: zhaoyun
"""
description:

"""
import threading
from threading import  Thread,Event
# 无参 的MyTime
class MyTimer(Thread):
    def __init__(self, interval, function, args=None, kwargs=None):
        Thread.__init__(self)
        self.interval = interval
        self.function = function
        self.finished = Event()
    # def cancel(self):
        # self.finished.set()
    def run(self):
        self.finished.wait(self.interval)
        if not self.finished.is_set():
            self.function( )
        self.finished.set()
def add( ):
    print( 3)

t=  MyTimer(2,add )
t.start()