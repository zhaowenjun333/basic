#!/usr/bin/env python3.6
# coding: utf-8
#socket_server_Thread.PY
# Created on 2017/12/18
# @author: zhaoyun
"""
description:

"""
import socket,threading
class chatserver:
    def __init__(self):
        self.HOST='127.0.0.1'
        self.PORT=9527
        self.BUFFER=4096
        self.event=threading.Event()
        self.sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.clients={}
    def listen(self):
        self.sock.bind(( self.HOST, self.PORT))
        self.sock.listen(3)
        # print('tcpServer listen at: %s:%s\n\r' %(HOST,PORT))
        threading.Thread(target=self._accept,name="accept").start()

    def _accept(self):
        while not self.event.isset():
            client_sock, client_addr = self.sock.accept()  # 阻塞,没有新的buffer 当前的链接断开,
            self.clients[client_addr]=client_sock
            threading.Thread(target=self._recv,args=(client_sock)).start()
    def _recv(self,client_sock):
        while not self.event.is_set():
            recv = client_sock.recv(self.BUFFER)  # 阻塞
            if not recv:
                client_sock.close()
                break

            for client_sock in self.clients.values():
                client_sock.send('tcpServer has received your message'.encode())
            if (False):
                self.sock.close()
    def stop(self):
        pass