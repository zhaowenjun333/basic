#!/usr/bin/env python3.6
# coding: utf-8
#dispatcher_r.PY
# Created on 2017/11/17
# @author: zhaoyun
class Dispatcher:
    def __init__(self):
        self._run()

    def _run(self):
        while True:
            cmd=input("plz input a method")
            if cmd.strip()=="quit":
                break
            getattr(self,cmd,lambda :print("default method",cmd))()

d =Dispatcher()