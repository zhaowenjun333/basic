#!/usr/bin/env python3.6
# coding: utf-8
#myfirstserver.PY
# Created on 2017/12/25
# @author: zhaoyun
"""
description:

"""
from wsgiref.simple_server  import make_server
from webob import dec
import webob
def index( ):
    return "<h1>index</h1>"
def showpython():
    return "<h1>showpython</h1>"
def notfound():
    return "<h1>not found</h1>"
def register(path, handler):
    rountable[path] = handler

rountable={
    # "/":index,
    # "/python":showpython
}

#可以通过配置文件方式读取进来成为一个字典,遍历字典把他们放到一个register方法中,从而达到动态注册的目的
register("/",index)
register("/python",showpython)

@dec.wsgify
def app(request:webob.Request):
    path = request.path
    return webob.Response(rountable.get(path,notfound)())  # rountable.get(path,notfound)()  是字符串

if __name__ == '__main__':
    httpd = make_server('0.0.0.0', 8000, app)
    httpd.serve_forever()


