#!/usr/bin/env python3.6
# coding: utf-8
#socket_server.PY
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
        self.sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    def listen(self):
        self.sock.bind(( self.HOST, self.PORT))
        self.sock.listen(3)
        # print('tcpServer listen at: %s:%s\n\r' %(HOST,PORT))
        threading.Thread(target=self._accept,name="accept")

    def _accept(self):
        while True:
            client_sock, client_addr = self.sock.accept()  # 阻塞,没有新的buffer 当前的链接断开,
            print('%s:%s connect' % client_addr)
            while True:
                recv = client_sock.recv(self.BUFFER)  # 阻塞
                if not recv:
                    client_sock.close()
                    break
                print('[Client %s:%s said]:%s' % (client_addr[0], client_addr[1], recv))
                client_sock.send('tcpServer has received your message'.encode())
                if ( False):
                    self.sock.close()

    def stop(self):
        pass