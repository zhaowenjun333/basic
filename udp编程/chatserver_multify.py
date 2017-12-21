#!/usr/bin/env python3.6
# coding: utf-8
#chatserver.PY
# Created on 2017/12/20
# @author: zhaoyun
"""
description:

"""
import  socket,time,threading
class Chatserver:
    def __init__(self):
        self.sock = socket.socket(type=socket.SOCK_DGRAM)
        self.sock.bind(("127.0.0.1",9988))
        self.event=threading.Event()
        self.cs = set()

    def run(self):
        threading.Thread(target=self.recv).start()

    def recv(self):
        while not self.event.is_set():
            data, clientaddr = self.sock.recvfrom(1024)
            self.cs.add(clientaddr)
            time.sleep(2)
            print(clientaddr)
            msg = "ack {}".format(data.decode())
            for c in self.cs:
                self.sock.sendto(msg.encode(), c)

    def stop(self):
        self.sock.close()

if __name__=="__main__":
    cs =Chatserver()
    cs.run()

