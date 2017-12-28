#!/usr/bin/env python3.6
# coding: utf-8
#myfirstserver.PY
# Created on 2017/12/25
# @author: zhaoyun
"""
description:

"""
from wsgiref.simple_server  import make_server
from webob import dec,exc
import webob
class Application:
    def notfound(self):
        return "<h1>not found</h1>"

    @classmethod
    def register(cls,path, handler):
        cls.rountable[path] = handler

    rountable={ }

    @dec.wsgify
    def __call__(self,  request:webob.Request):
        try:
            return webob.Response(self.rountable.get(request.path)())  # rountable.get(path,notfound)()  是字符串
        except:
            raise exc.HTTPNotFound("OOOO已经被外星人劫持")

def index( ):
    return "<h1>index</h1>"
def showpython():
    return "<h1>showpython</h1>"
    #可以通过配置文件方式读取进来成为一个字典,遍历字典把他们放到一个register方法中,从而达到动态注册的目的
Application.register("/",index)
Application.register("/python",showpython)


if __name__ == '__main__':
    httpd = make_server('0.0.0.0', 8000, Application())
    httpd.serve_forever()


