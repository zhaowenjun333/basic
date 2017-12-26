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
@dec.wsgify
def app(request:webob.Request):
    print(request.path)
    path = request.path
    if path =="/index.html":
        body = "<h1>hai</h1>"
    else:
        body="Errot page"
    return webob.Response(body)

if __name__ == '__main__':
    httpd = make_server('127.0.0.1', 8000, app)
    httpd.serve_forever()


