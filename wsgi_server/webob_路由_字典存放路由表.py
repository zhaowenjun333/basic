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

rountable={
    "/":index,
    "/python":showpython
}

@dec.wsgify
def app(request:webob.Request):
    print(request.path)
    print(request.params)
    path = request.path

    # if path =="/index.html":
    #     body = "<h1>hai</h1>"
    # else:
    #     body="Errot page"
    return webob.Response(rountable.get(path,notfound)())  # rountable.get(path,notfound)()  是字符串

if __name__ == '__main__':
    httpd = make_server('0.0.0.0', 8000, app)
    httpd.serve_forever()


