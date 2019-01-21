#!/usr/bin/env python3.6
# coding: utf-8
#testjointhread.PY
# Created on 2017/12/13
# @author: zhaoyun
"""
description:
join 的概念:  当前线程等待 ,让其他线程加入
"""
import time,threading
class A:
    def showme(self):
        time.sleep(1)
        print("lsllsl市领导发链接拉萨离开过阿里京东噶是的噶十多个阿萨德记录卡的距离国家")
        time.sleep(3)

if __name__=="__main__":
    t1=threading.Thread(target=A().showme,daemon=True)  # 主线程不会等待子线程
    # t1.start()
    t1.run()
    # t1.join()
    # while 1:
    #     print("iam mai thread")