#!/usr/bin/env python3.6
# coding: utf-8
# conpool.PY
# Created on 2018/1/4
# @author: zhaoyun
"""
description:

"""
import pymysql
import threading
from queue import Queue


class ConnPoo:
    def __init__(self, size, *args, **kwargs):
        self._size_max = size
        self._pool = Queue(size)
        self.local = threading.local()
        for i in range(size):
            conn = pymysql.connect(*args, **kwargs)
            self._pool.put(conn)

    @property
    def size_max(self):
        return self._size_max

    def get_conn(self):
        conn = self._pool.get()
        self.local.conn = conn
        return conn

    def return_conn(self, conn):
        if isinstance(conn, pymysql.connections.Connection):
            self._pool.put(self.local.conn)

    def __enter__(self):
        if getattr(self.local, "conn", None) is None:
            self.local.conn = self.get_conn()
        return self.local.conn.cursor()

    def __exit__(self, exc_type, exc_val, exc_tb):
        if exc_type:
            self.local.conn.rollback()
        else:
            self.local.conn.commit()
        self.return_conn(self.local.conn)
        self.local.conn = None


pool = ConnPoo(3,"172.16.101.67","root","123456","test")

#
with pool as cursor:
    with cursor:
        cursor.execute("select * from salaries")
        for i in cursor:
            print(i)

while True:
    pass