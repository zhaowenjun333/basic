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

def demo_app_my(environ:dict, start_response):
    request = webob.Request(environ)
    res = webob.Response()
    res.status_code=200
    html = "<html>nihaoma</html>".encode("utf-8")
    res.body=html
    return  res(environ,start_response)    #__call__

@dec.wsgify
def app(request:webob.Request):
    return webob.Response("hai")

if __name__ == '__main__':
    httpd = make_server('127.0.0.1', 8000, app)
    httpd.serve_forever()


