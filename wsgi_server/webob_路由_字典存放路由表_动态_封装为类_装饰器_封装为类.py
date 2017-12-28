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
import webob,re
class Application:
    rountable=[]

    @classmethod
    def register(cls,path):
        def wrap(handler):
            # cls.rountable[path] = handler
            cls.rountable.append((re.compile(pattern=path),handler))
            return handler
        return  wrap

    @dec.wsgify
    def __call__(self,  request:webob.Request):
        for pattern,handler in self.rountable:
            if pattern.match(request.path):
                return webob.Response(handler())
        # try:
        #     return webob.Response(self.rountable.get(request.path)())  # rountable.get(path,notfound)()  是字符串
        # except:
        #     raise exc.HTTPNotFound("OOOO已经被外星人劫持")
@Application.register("^/$")    #index() = Application.register("/")(index) () # index()
def index():
    return "<h1>index</h1>"
@Application.register("^/python$")
def showpython():
    return "<h1>showpython</h1>"

if __name__ == '__main__':
    httpd = make_server('0.0.0.0', 8000, Application())
    httpd.serve_forever()


