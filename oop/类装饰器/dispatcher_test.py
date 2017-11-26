#!/usr/bin/env python3.6
# coding: utf-8
#dispatcher_test.PY
# Created on 2017/11/17
# @author: zhaoyun
from queue  import Queue
class dispatcher():
    def cmd1(self):
        print('cdm1')
    def reg(self,cmd, fn):
        if isinstance(cmd, str):
            setattr(self.__class__,cmd,fn)
        else:
            print("error")
    def run(self):
        while True:
            cmd= input("plz input command:")
            if cmd.strip()=="quit":
                return
            getattr(self,cmd.strip(),self.defaultfn)()

    def defaultfn(self):
        pass

dis=dispatcher()
dis.reg('cmd1',lambda self:print(1))
dis.reg('cmd3',lambda self:3)
dis.run()