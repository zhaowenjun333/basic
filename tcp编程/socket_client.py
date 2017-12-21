#!/usr/bin/env python3.6
# coding: utf-8
#socket_client.PY
# Created on 2017/12/18
# @author: zhaoyun
"""
description:

"""
import socket,time
HOST='127.0.0.1'
PORT=9527
BUFFER=4096
sock=socket.socket(socket.AF_INET,socket.SOCK_STREAM)
sock.connect((HOST,PORT))
count =0

while True:
    count+=1
    sock.send('the {}times hello, tcpServer!'.format(count).encode())
    recv=sock.recv(BUFFER)
    print('the {}times [tcpServer said]: %s'.format(count) % recv)
    time.sleep(1)
sock.close()
