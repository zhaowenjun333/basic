#!/usr/bin/env python3.6
# coding: utf-8
#test__call__.PY
# Created on 2017/11/24
# @author: zhaoyun
class Adder:
    def __call__(self, *args, **kwargs):
        ret = 0
        for i in args:
            ret +=i
        self.ret = ret
        return  ret

adder = Adder()
t = adder(1,2,4)
print(t)
print(adder.ret)