#!/usr/bin/env python3.6
# coding: utf-8
# socket_server_Thread.PY
# Created on 2017/12/18
# @author: zhaoyun
"""
description:
ox01
netstat -anp tcp|find "9527"
"""
import socket, threading, logging, time


class chatserver:
    def __init__(self):
        self.HOST = '127.0.0.1'
        self.PORT = 9527
        self.BUFFER = 1024
        self.event = threading.Event()
        self.sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.clients = {}
        self.lock = threading.Lock()

    def listen(self):
        self.sock.bind((self.HOST, self.PORT))
        self.sock.listen()
        logging.info(">>>>>>")  # 不传参    也可以打印
        threading.Thread(target=self._accept, name="accept").start()

    def _accept(self):
        while not self.event.is_set():
            try:
                client_sock, client_addr = self.sock.accept()  # 阻塞,
                self.clients[client_addr] = client_sock
                threading.Thread(target=self._recv, args=(client_sock, client_addr)).start()
            except Exception as e:
                print(e)

    def _recv(self, client_sock, client_addr):
        while not self.event.is_set():
            try:
                recv = client_sock.recv(self.BUFFER)  # 阻塞
                print(client_sock)

                print(client_addr[-1])    #port
                print(client_addr[-2])   #ip
                # print(type(client_addr))

                print("{}:{}".format(client_addr,recv))
                msg = recv.decode().strip()
                try:
                    for c in self.clients.values():  #client_sock
                        c.send('{}:{}'.format(client_addr, msg).encode())
                except  Exception   as e:
                    print("99999999999999999", e)
            except Exception   as e:
                print("****************", e)
                # self.clients.values().remove(client_sock)
                self.clients.pop(client_addr)
                client_sock.close()
                break

    def stop(self):
        for c in self.clients.values():
            c.close()
            self.sock.close()
            self.event.close()


if __name__ == "__main__":
    logging.basicConfig(level=logging.INFO)  # 这个一定要加载在   listen之前    ,这样不用手动传入参数
    chtsv = chatserver()
    chtsv.listen()
    e = threading.Event()


    def show_thread(e):
        while not e.is_set():
            time.sleep(5)
            logging.info(threading.enumerate())
            logging.warning(chtsv.clients.values())


    threading.Thread(target=show_thread, args=(e,), name='show_thread').start()
    # while not e.is_set():
    #     cmd =input(">>>").strip()
    #     if cmd =="quit":
    #         chtsv.stop()
    #         e.wait(3)
    #         break
