#!/usr/bin/env python3.6
# coding: utf-8
#testjointhread.PY
# Created on 2017/12/13
# @author: zhaoyun
"""
description:

"""
import time,threading
class A:
    def showme(self):
        time.sleep(1)
        print("lsllsl")


if __name__=="__main__":
    t1=threading.Thread(target=A().showme,daemon=True)  # 主线程不会等待子线程
    t1.start()
    t1.join()
    print("iam mai thread")