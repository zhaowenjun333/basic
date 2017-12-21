#!/usr/bin/env python3.6
# coding: utf-8
#socket_server_Thread.PY
# Created on 2017/12/18
# @author: zhaoyun
"""
description:

"""
import socket,threading,logging,time
class chatserver:
    def show(self):
        print(333)
        print(x)                      #主线程内的对象可以使用全局变量
        logging.info("wgsgssh")

if __name__=="__main__":
    x =0                             # 主线程定义的变量
    logging.basicConfig(level=20)    # 主线程定义的变量
    chtsv=chatserver()
    chtsv.show( )
