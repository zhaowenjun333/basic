#!/usr/bin/env python3.6
# coding: utf-8
#webob_app_router.PY
# Created on 2017/12/27
# @author: zhaoyun
"""
description:

"""
from wsgiref.simple_server  import make_server
import re
from webob import Response,Request,dec,exc


class Dict2Obj:
    def __init__(self,d:dict):
        self.__dict__["_dict"]=d

    def __getattr__(self, item):
        try:
            return self._dict[item]
        except KeyError:
            raise AttributeError("Attribute {} not found".format(item))

    def __setattr__(self, key, value):
        raise NotImplementedError


class Router:
    TYPECAST = {
        "str": str,
        "word": str,
        "int": int,
        "float": float,
        "ant": str
    }

    TYPE_PATTERN = {
        "str": r"[^/]+",
        "word": r"\w+",
        "int": r"[+-]?\d+",
        "float": r"[+-]?\d+\.\d+",
        "and": r".+"
    }

    pattern = re.compile("/({[^{}:]+:?[^{}:]*})")

    def __init__(self,prefix:str=""):
        self.__prefix=prefix.rstrip("/\\")
        self.__routetable=[]

    @property
    def prefix(self):
        return self.__prefix

    def route(self,pattern,*method):
        def wrapper(handler):
            self.__routetable.append((method,re.compile(pattern),handler))
            return handler
        return wrapper

    def get(self,pattern):
        return self.route(pattern,"GET")

    def post(self,pattern):
        return self.route(pattern,"POST")

    def match(self,request:Request):
        if not request.path.startswith(self.prefix):   # 减少嵌套层次
            return
        for methods,pattern,handler in self.__routetable:
            if not methods or request.method.upper() in methods:
                matcher= pattern.match(request.path.replace(self.prefix,""))   # pattern 是prefix后面的所以匹配的字符串也需要去掉prefix
                if matcher:
                    request.args = matcher.group()
                    request.kwargs= Dict2Obj(matcher.groupdict())  # request.kwargs.k   -> k可以直接访问
                    return handler(request)


class Application:
    ROUTERs= []

    @classmethod
    def register(cls,router:Router):
        cls.ROUTERs.append(router)

    @dec.wsgify
    def __call__(self, request:Request):
        for router in self.ROUTERs:
            response = router.match(request)
            if response:                # handler(request)  返回的就是处理好的对象,直接抛给浏览器
                return response
        raise exc.HTTPNotFound("wrongpage")


idx = Router()         # prefix
py = Router("/python")
Application.register(idx)  # prefix注册,前缀应用名很少,所以这个是可以定义有限个很少..
Application.register(py)   # 路由对象已经注册到应用中的类属性了,


@idx.get("^/$")
def index(request:Request):
    res = Response()
    res.status_code=200
    # res.content_type="text/html"
    res.text="<h1>mage</h1>"
    return res


@idx.get("^/python$")
def index(request:Request):
    res = Response()
    res.status_code=200
    # res.content_type="text/html"
    res.text="<h1>mage pythonroot</h1>"
    return res


@py.route("^/(\w+)$")
def showpython(request:Request):
    res = Response()
    res.text="mage to python"
    return res


if __name__ == '__main__':
    httpd = make_server('0.0.0.0', 8000, Application())
    httpd.serve_forever()