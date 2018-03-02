#!/usr/bin/env python3.6
# coding: utf-8
"""
description:
可调用函数,
路由
"""
from webob import Response, Request, dec, exc
from .context import Context,NestedContext
from .dict2obj import Dict2Obj
import re


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
        return "/(?P<{}>{})".format(name, self.TYPE_PATTERN.get(type_param, "\w+")), name, self.TYPECAST.get(type_param,
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
        self.pre_intercepter = []
        self.post_intercepter = []
        self.ctx = NestedContext()

    @property
    def prefix(self):
        return self.__prefix

    def register_preintercepter(self, fn):
        self.pre_intercepter.append(fn)
        return fn

    def register_postintercepter(self, fn):
        self.post_intercepter.append(fn)
        return fn

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
        for fn in self.pre_intercepter:
            request = fn(self.ctx, request)
        for methods, pattern, translator, handler in self.__route_table:
            if not methods or request.method.upper() in methods:
                matcher = pattern.match(request.path.replace(self.prefix, ""))  # pattern 是prefix后面的所以匹配的字符串也需要去掉prefix
                if matcher:
                    new_dict = {}
                    for k, v in matcher.groupdict().items():
                        new_dict[k] = translator[k](v)
                    request.kwargs = Dict2Obj(new_dict)  # request.kwargs.k   -> k可以直接访问
                    response = handler(request)
                    for fn in self.post_intercepter:
                        response = fn(self.ctx, request, response)
                    return response


class Luckynginx:
    ctx = Context()
    Router = Router
    Response=Response
    Request = Request

    def __init__(self, **kwargs):
        self.ctx.app = self
        for k, v in kwargs:
            self.ctx[k] = v

    ROUTERs = []

    PRE_INTERCEPTER = []

    POST_INTERCEPTER = []

    @classmethod
    def register_preintercepter(cls, fn):
        cls.PRE_INTERCEPTER.append(fn)
        return fn

    @classmethod
    def register_postintercepter(cls, fn):
        cls.POST_INTERCEPTER.append(fn)
        return fn

    @classmethod
    def register(cls, router: Router):
        router.ctx.relate(cls.ctx)
        router.ctx.router = router
        cls.ROUTERs.append(router)

    @dec.wsgify
    def __call__(self, request: Request):
        for fn in self.PRE_INTERCEPTER:
            request = fn(self.ctx, request)
        for router in self.ROUTERs:
            response = router.match(request)
            for fn in self.POST_INTERCEPTER:
                response = fn(self.ctx, request, response)
            if response:  # handler(request)  返回的就是处理好的对象,直接抛给浏览器
                return response
        raise exc.HTTPNotFound("wrongpage")