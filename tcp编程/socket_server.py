#!/usr/bin/env python3.6
# coding: utf-8
#socket_server.PY
# Created on 2017/12/18
# @author: zhaoyun
"""
description:

"""

import socket
HOST='127.0.0.1'
PORT=9527
BUFFER=4096
sock=socket.socket(socket.AF_INET,socket.SOCK_STREAM)
sock.bind((HOST,PORT))
sock.listen(3)
print('tcpServer listen at: %s:%s\n\r' %(HOST,PORT))
while True:
   client_sock,client_addr=sock.accept()     #阻塞,没有新的buffer 当前的链接断开,
   print('%s:%s connect' %client_addr)
   while True:
     recv=client_sock.recv(BUFFER)             #阻塞
     if not recv:
       client_sock.close()
       break
     print('[Client %s:%s said]:%s' %(client_addr[0],client_addr[1],recv))
     client_sock.send('tcpServer has received your message'.encode())
sock.close()
