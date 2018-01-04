#!/usr/bin/env python3.6
# coding: utf-8
# Created on 2017/12/29
# @author: zhaoyun
"""
description:
this is a demo .please don't delete
"""

from wsgiref.simple_server import make_server
from luckynginx import jsonify
from luckynginx.web import Luckynginx
from luckynginx.context import Context

idx = Luckynginx.Router()  # prefix根
py = Luckynginx.Router("/python")  # prefix  python
Luckynginx.register(idx)  # prefix注册,前缀应用名很少,所以这个是可以定义有限个很少..
Luckynginx.register(py)  # 路由对象已经注册到应用中的类属性了,


@idx.get("^/$")
def index(request: Luckynginx.Request):  # index = idx.get("^/$")(index)   ->index = idx.route(self, rule, *method)(index)
    res = Luckynginx.Response()
    res.status_code = 200
    # res.content_type="text/html"
    print(request)
    res.text = "<h1>luckynginx</h1>"
    return res


@idx.get("/{id:int}")
def index1(request: Luckynginx.Request):  # index = idx.get("^/$")(index)   ->index = idx.route(self, rule, *method)(index)
    res = Luckynginx.Response()
    res.status_code = 200
    # res.content_type="text/html"
    print(request)
    res.text = "<h1>luckynginx->{}</h1>".format(request.kwargs.id)
    return res


@idx.get("^/python$")
def index(request: Luckynginx.Request):
    res = Luckynginx.Response()
    res.status_code = 200
    print(request)
    # res.content_type="text/html"
    res.text = "<h1>luckynginx_python</h1>"
    return res


# @py.route("^/(\w+)$")
# def show_python(request: Request):
#     print(request)
#     res = Response()
#     res.text = "ma_ge to python"
#     return res


@py.route("/{product:str}")
def show_python_product(request: Luckynginx.Request):
    print(request)
    res = Luckynginx.Response()
    res.text = "product no.{}".format(request.kwargs.product)
    return res


@Luckynginx.register_preintercepter
def show_headers(ctx: Context, request: Luckynginx.Request):
    print(ctx.items())
    print(request.path, "Paaaaaaaaaaaaaaaath")
    print(request.user_agent, "agentttttttttttt")
    return request


@py.register_preintercepter
def show_prefix(ctx: Context, request: Luckynginx.Request):
    print("_____________prefix = {}".format(ctx.router.prefix))
    return request


@py.register_postintercepter
def show_json(ctx,request,response):
    body = response.body.decode()
    return jsonify(body= body)


if __name__ == '__main__':
    httpd = make_server('0.0.0.0', 8000, Luckynginx())
    try:
        httpd.serve_forever()
    except KeyboardInterrupt:
        httpd.shutdown()
        httpd.server_close()