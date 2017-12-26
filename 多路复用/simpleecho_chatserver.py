#!/usr/bin/env python3.6
# coding: utf-8
#simpleecho.PY
# Created on 2017/12/22
# @author: zhaoyun
"""
description:

"""
import selectors
import socket
import threading
selector=selectors.DefaultSelector()
def accept(sock:socket.socket):
    conn,client=sock.accept()
    conn.setblocking(False)
    selector.register(conn,selectors.EVENT_READ,recv)

def recv(conn:socket.socket):
    data=conn.recv(1024).strip().deconde()
    print(data)
    msg = "your msg={}".format(data)
    selector.register(conn, selectors.EVENT_READ, send)

def send(client:socket.socket):
    client.send(msg.encode())
    pass

sock=socket.socket()
ip="127.0.0.1"
port=9990
addr=(ip,port)
sock.bind(addr)
sock.listen()
sock.setblocking(False)
e=threading.Event()
key = selector.register(sock,selectors.EVENT_READ,accept)
print(key)

while not e.is_set():
    events=selector.select()   #阻塞
    if events:
        print(events)
    for key,mask in events:
        print(key,mask)
        callback=key.data
        callback(key.fileobj)
