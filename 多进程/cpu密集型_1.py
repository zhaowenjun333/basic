#!/usr/bin/env python3.6
# coding: utf-8
#cpu密集型.PY
# Created on 2017/12/18
# @author: zhaoyun
"""
description:
假多进程其实还是计算100000000这个数   多次计算最后产生 5个 100000000
"""
import multiprocessing,datetime
def calc(i):
    print("i'am {}".format(i))
    sum=0
    for _ in range(100000000):
        sum+=1

if __name__=="__main__":
    start=datetime.datetime.now()
    plst=[]
    lock = multiprocessing.Lock()
    for i in range(5):
        p=multiprocessing.Process(target=calc,args=(i,),name="process-{}".format(i))
        p.start()
        plst.append(p)
    for p in plst:
        p.join()   # 主线程不干活,但需要等待其他的五个线程干活

    delta=(datetime.datetime.now()-start).total_seconds()
    print(delta)