#!/usr/bin/env python3.6
# coding: utf-8
#dispatcher_test.PY
# Created on 2017/11/17
# @author: zhaoyun
from queue  import Queue

def dispatcher( ):
    cmds={}
    def register(cmd,fn):
        if isinstance(cmd,str):
            cmds[cmd]=fn
        else:
            print("error")
    def run():
        while True:
            cmd= input("plz input command:")
            if cmd.strip()=="quit":
                return
            cmds.get(cmd.strip(),defaultfn)()
    def defaultfn():
        pass
    return  register, run
reg,run=dispatcher()
reg('cmd1',lambda :1)
reg('cmd3',lambda :3)
print(run())