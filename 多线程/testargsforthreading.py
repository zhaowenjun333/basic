#!/usr/bin/env python3.6
# coding: utf-8
#testargsforthreading.PY
# Created on 2017/12/13
# @author: zhaoyun
"""
description: 带参数的多线程

"""
import sys
sys.path
import threading,time
from 多线程 import teacherduoxianc    #threading模块的公共方法
# print(sys.path)
#线程的公共代码
def subwork(n:int):
    teacherduoxianc.getinfoThread()
    while True:
        for i in range(5):
            time.sleep(0.5)
            print('this is {} thread'.format(n))

threads=[]  #线程池子
for i in range(1,4):
    t=threading.Thread(target=subwork,args=(i,))
    threads.append(t)

if __name__=="__main__":
    #子线程启动
    for t in threads:
        t.start()

    #主线程
    while True:
        teacherduoxianc.getinfoThread()
        time.sleep(3)
        print("iam main thread")





