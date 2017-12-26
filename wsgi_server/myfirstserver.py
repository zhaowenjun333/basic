#!/usr/bin/env python3.6
# coding: utf-8
#myfirstserver.PY
# Created on 2017/12/25
# @author: zhaoyun
"""
description:

"""
from wsgiref.simple_server import make_server

def demo_app_my(environ,start_response):
    h = sorted(environ.items())
    for k, v in h:
        print(k, '=', repr(v) )
    environ.get("")
    html="<html>nihaoma</html>"
    start_response("200 OK", [('Content-Type', 'text/plain; charset=utf-8')])
    return [html.encode()]


if __name__ == '__main__':
    # ip='127.0.0.1'
    # port=9999
    httpd=make_server('127.0.0.1', 8000, demo_app_my)
    httpd.serve_forever()
