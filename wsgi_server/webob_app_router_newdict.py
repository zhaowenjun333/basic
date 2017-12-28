#!/usr/bin/env python3.6
# coding: utf-8
# webob_app_router_newdict.PY
# Created on 2017/12/28
# @author: zhaoyun
"""
description:

"""
from wsgiref.simple_server import make_server
import re
from webob import Response, Request, dec, exc


class Dict2Obj:
    def __init__(self, d: dict):
        self.__dict__["_dict"] = d

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

    PATTERN = re.compile("/({[^{}:]+:?[^{}:]*})")

    def transform(self, kv: str):
        name, type_param = kv.strip("/{}").split(":")
        return "/(?<{}>{})".format(name, self.TYPE_PATTERN.get(type_param, "\w+")), name, self.TYPECAST.get(type_param,
                                                                                                            str)

    def parse(self, src: str):
        start = 0
        res = ""
        translator = {}
        while True:
            matcher = self.PATTERN.search(src, start)
            if matcher:
                res += matcher.string[start:matcher.start()]
                tmp = self.transform(matcher.string[matcher.start():matcher.end()])
                # <class 'tuple'>: ('/(?<name>[^/]+)', 'name', <class 'str'>)
                res += tmp[0]
                translator[tmp[1]] = tmp[2]
                start = matcher.end()
            else:
                break
        if res:
            return res, translator
        else:
            return src, translator

    def __init__(self, prefix: str = ""):
        self.__prefix = prefix.rstrip("/\\")
        self.__route_table = []

    @property
    def prefix(self):
        return self.__prefix

    def route(self, rule, *method):
        def wrapper(handler):
            pattern, translator = self.parse(rule)
            self.__route_table.append((method, re.compile(pattern), translator, handler))
            return handler
        return wrapper

    def get(self, pattern):
        return self.route(pattern, "GET")  # 加了一个参数已经形成调用条件了

    def post(self, pattern):
        return self.route(pattern, "POST")

    def match(self, request: Request):
        if not request.path.startswith(self.prefix):  # 减少嵌套层次
            return
        for methods, pattern, translator, handler in self.__route_table:
            if not methods or request.method.upper() in methods:
                matcher = pattern.match(request.path.replace(self.prefix, ""))  # pattern 是prefix后面的所以匹配的字符串也需要去掉prefix
                if matcher:
                    new_dict = {}
                    for k, v in matcher.groupdict().items():
                        new_dict[k] = translator[k](v)
                    request.kwargs = Dict2Obj(new_dict)  # request.kwargs.k   -> k可以直接访问
                    return handler(request)


class Application:
    ROUTERs = []

    @classmethod
    def register(cls, router: Router):
        cls.ROUTERs.append(router)

    @dec.wsgify
    def __call__(self, request: Request):
        for router in self.ROUTERs:
            response = router.match(request)
            if response:  # handler(request)  返回的就是处理好的对象,直接抛给浏览器
                return response
        raise exc.HTTPNotFound("wrongpage")


idx = Router()  # prefix根
py = Router("/python")  # prefix  python
Application.register(idx)  # prefix注册,前缀应用名很少,所以这个是可以定义有限个很少..
Application.register(py)  # 路由对象已经注册到应用中的类属性了,


@idx.get("^/$")
def index(request: Request):   # index = idx.get("^/$")(index)   ->index = idx.route(self, rule, *method)(index)
    res = Response()
    res.status_code = 200
    # res.content_type="text/html"
    print(request)
    res.text = "<h1>ma_ge</h1>"
    return res


@idx.get("^/python$")
def index(request: Request):
    res = Response()
    res.status_code = 200
    print(request)
    # res.content_type="text/html"
    res.text = "<h1>ma_ge python_root</h1>"
    return res


@py.route("^/(\w+)$")
def show_python(request: Request):
    print(request)
    res = Response()
    res.text = "ma_ge to python"
    return res


if __name__ == '__main__':
    httpd = make_server('0.0.0.0', 8000, Application())
    httpd.serve_forever()
