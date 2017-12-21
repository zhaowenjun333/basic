#!/usr/bin/env python3.6
# coding: utf-8
#teacher_server.PY
# Created on 2017/12/18
# @author: zhaoyun
"""
description:

"""
import threading
import time
import socket
import logging
logging.basicConfig(format='%(thread)s %(threadName)s %(message)s',level=20)
socket=socket.socket()
addr=('127.0.0.1',9999)
socket.bind( addr)
socket.listen()

client_sock,client_addr=socket.accept()
logging.info(client_sock)
data=client_sock.recv(1024)
client_sock.send(data)
logging.info(socket)
client_sock.close()
socket.close()
