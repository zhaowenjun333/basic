#!/usr/bin/env python3.6
# coding: utf-8
#socket_client.PY
# Created on 2017/12/18
# @author: zhaoyun
"""
description:

"""
import socket,time,threading,logging
class Client:
    def __init__(self):
        self.HOST='127.0.0.1'
        self.PORT=9527
        self.BUFFER = 4096
        self.sock=socket.socket(socket.AF_INET,socket.SOCK_STREAM)
        self.event = threading.Event()

    def run(self):
        self.sock.connect((self.HOST,self.PORT))
        threading.Thread(target=self.acceptmsg).start()
        while not self.event.is_set():
            msg = input(">>>").strip()
            if msg =="quit":
                self.sock.close()
                self.event.set()
            else:
                print(self.sock)
                self.sock.send('{} said:{}'.format(self.sock,msg ).encode())

    def acceptmsg(self):
        while  not self.event.is_set():
            recv=self.sock.recv(self.BUFFER)
            print(recv)
    def stopconnet(self):
        self.sock.close()

if __name__=="__main__":
    clt = Client()
    clt.run()
