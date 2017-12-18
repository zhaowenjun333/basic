#!/usr/bin/env python3.6
# coding: utf-8
#tenworkermade1000cups_Lock.PY
# Created on 2017/12/16
# @author: zhaoyun
"""
description:
Lock   只有acquire 和  release 方法
"""
import threading,logging,time
class madecupstogether:
    def __init__(self,count):
        self.count=count
    def worker(self,cups,e):
        while True:
            lock.acquire()
            try:
                if len(cups)<self.count:
                    # time.sleep(0.5)  #还在当前线程 并没有告诉cpu切换线程
                    e.wait(0.1)
                    cups.append(1)
                else:
                    break
            finally:
                logging.info("worker{} has made cups up to {}".format(threading.current_thread().name,len(cups)))
                lock.release()
if __name__=="__main__":
    cups = []
    lock: threading.Lock = threading.Lock()
    logging.basicConfig(level=20)
    madecupstogether=madecupstogether(10000)
    e =threading.Event()
    for i in range(10):
        t = threading.Thread(target=madecupstogether.worker,args=(cups,e ))
        t.start()



