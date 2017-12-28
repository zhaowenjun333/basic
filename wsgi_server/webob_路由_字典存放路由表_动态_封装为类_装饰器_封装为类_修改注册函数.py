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
    def post(cls,path):
        return  cls.router("POST",path)

    @classmethod
    def get(cls,path):
        return  cls.router("GET",path)

    @classmethod
    def router(cls,method,path):
        def wrap(handler):
            cls.rountable.append((method,re.compile(pattern=path),handler))
            return handler
        return  wrap

    @dec.wsgify
    def __call__(self,  request:webob.Request):
        for method,pattern,handler in self.rountable:
            if request.method.upper() != method:
                continue
            if pattern.match(request.path):
                return webob.Response(handler())
        raise exc.HTTPNotFound("你访问的也main被外星人拦截")

@Application.get("^/$")
def index():
    return "<h1>index</h1>"

@Application.post("/python")
def showpython():
    return "<h1>showpython</h1>"

if __name__ == '__main__':
    httpd = make_server('0.0.0.0', 8000, Application())
    httpd.serve_forever()


