
#!/usr/bin/env python3.6
# coding: utf-8
#listenandsee.PY
# Created on 2017/12/12
# @author: zhaoyun
"""
description:

"""
#coding=utf-8
import threading
from time import ctime,sleep
import time


def music(func):
    for i in range(2):
        print("I was listening to %s . %s" %(func,ctime()))
        sleep(1)

def move(func):
    for i in range(2):
        print("I was at the %s! %s" %(func,ctime()))
        sleep(5)

threads = []
t1 = threading.Thread(target=music,args=(u'爱情买卖',))
threads.append(t1)
t2 = threading.Thread(target=move,args=(u'阿凡达',))
threads.append(t2)

if __name__ == '__main__':
    for t in threads:
        t.setDaemon(True)
        t.start()

    time.sleep(2)
    print("all over %s" %ctime())