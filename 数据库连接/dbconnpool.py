#!/usr/bin/env python3.6
# coding: utf-8
#dbconnpool.PY
# Created on 2018/1/4
# @author: zhaoyun
"""
description:

"""
import  threading
import pymysql
class DbConPool:
    def __init__(self):
        self.s =threading.Semaphore(3)
        self.con = pymysql.Connect("172.16.101.56","root","123456","test")

    def get(self):
        with self.con as cursor:
            self.s.acquire()

    def ret(self):
        self.s.acquire()
        cursor.close()