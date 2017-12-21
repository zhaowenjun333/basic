#!/usr/bin/env python3.6
# coding: utf-8
#chatserver.PY
# Created on 2017/12/20
# @author: zhaoyun
"""
description:

"""
import  socket,time
sock = socket.socket(type=socket.SOCK_DGRAM)
addr =("127.0.0.1",9988)
sock.bind(addr)

data,clientaddr=sock.recvfrom(1024)
time.sleep(2)
print(clientaddr)

msg="ack {}".format(data.decode())
sock.sendto(msg.encode(),clientaddr)

sock.close()
