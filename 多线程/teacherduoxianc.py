#!/usr/bin/env python3.6
# coding: utf-8
#teacherduoxianc.PY
# Created on 2017/12/13
# @author: zhaoyun
"""
description:

"""
import threading,time
def getinfoThread():
    print(threading.enumerate())
    print(threading.current_thread())
    print(threading. active_count())
    print(threading.get_ident())

def worker():
    getinfoThread()
    for i in range(4):
        time.sleep(1)
        print("eee")

if __name__== "__main__":
    t =threading.Thread(target=worker,name="sub")
    t.start()
    time.sleep(6)
    getinfoThread()
    print("eer")