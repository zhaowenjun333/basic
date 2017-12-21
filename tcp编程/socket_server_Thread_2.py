#!/usr/bin/env python3.6
# coding: utf-8
#socket_server_Thread.PY
# Created on 2017/12/18
# @author: zhaoyun
"""
description:

"""
import socket,threading,logging,time
class chatserver:
    def __init__(self):
        self.HOST='127.0.0.1'
        self.PORT=9527
        self.BUFFER=4096
        self.event=threading.Event()
        self.sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.clients={}

    def listen(self ):
        self.sock.bind(( self.HOST, self.PORT))
        self.sock.listen(3)
        logging.info("(((((((((")   #不传参    也可以打印
        threading.Thread(target=self._accept,name="accept").start()
        print("222")

    def _accept(self):
        while not self.event.is_set():
            try:
                client_sock, client_addr = self.sock.accept()  # 阻塞,三次 self.sock.listen(3)

                self.clients[client_addr]=client_sock
                threading.Thread(target=self._recv,args=(client_sock,)).start()
            except Exception as e:
                print(e)

    def _recv(self,client_sock):
        while not self.event.is_set():
            try:
                recv = client_sock.recv(self.BUFFER)  # 阻塞
            except Exception  as e :
                print(e)
            msg=recv.decode().strip()
            for client_sock in self.clients.values():
                client_sock.send('tcpServer has received your message{}'.format(msg).encode())
    def stop(self):
        pass

if __name__=="__main__":
    logging.basicConfig(level=logging.INFO)          # 这个一定要加载在   listen之前    ,这样不用手动传入参数
    chtsv=chatserver()
    chtsv.listen( )
    e =threading.Event()
    def show_thread(e):
        while not e.is_set():
            time.sleep(2)
            logging.info(threading.enumerate())
    threading.Thread(target=show_thread,args=(e,),name='show_thread').start()
    # while not e.is_set():
    #     cmd =input(">>>").strip()
    #     if cmd =="quit":
    #         chtsv.stop()
    #         e.wait(3)
    #         break
