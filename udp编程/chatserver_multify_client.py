#!/usr/bin/env python3.6
# coding: utf-8
# chatserver.PY
# Created on 2017/12/20
# @author: zhaoyun
"""
description:

"""
import socket, time, threading

sock = socket.socket(type=socket.SOCK_DGRAM)
try:
    sock.sendto("c1".encode(), ("127.0.0.1", 9988))     # 先发送是最好的解决方案, 在服务器里面注册一下
    data = sock.recv(1024)
    print(data)
except Exception as e :
    print("服务未开启,请先开启服务器")

class ChatClient:
    pass

