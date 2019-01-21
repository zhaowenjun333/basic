#!/usr/bin/env python3.6
# coding: utf-8
#waitingforsunthreading.PY
# Created on 2018/4/9
# @author: zhaoyun
"""
description:子线程执行过程中锁定，执行完成后和主线程共享cpu资源，当满足条件时候，子线程又会执行直到完成
然后子主线程一起共享资源，一直这样循环下去，直到主线程结束，所有线程结束
"""
from threading import  Thread ,Event,Lock
import time
lock:Lock = Lock()
def add():
    count =1
    while True:
        # time.sleep(3)
        if count%200 ==0:
            lock.acquire()
            print("rrrrr")
            time.sleep(2)
            print("wwwwww",count)
            lock.release()
        count += 1
t = Thread(target=add)
t.setDaemon(True)
t.start()

time.sleep(2)
print( "iam maiN threading  start")
time.sleep(10)
print("iam main threading end")
