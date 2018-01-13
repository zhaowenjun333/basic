#!/usr/bin/env python3.6
# coding: utf-8
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


class Context(dict):
    def __getattr__(self, item):
        try:
            return self[item]
        except KeyError:
            raise AttributeError("Attribute {} not Found".format(item))

    def __setattr__(self, key, value):
        self[key] = value


class NestedContext(Context):
    def __init__(self, globalcontext: Context = None):
        super().__init__()
        self.relate(globalcontext)

    def relate(self, globalcontext: Context = None):
        self.global_context = globalcontext

    def __getattr__(self, item):
        if item in self.keys():
            return self[item]
        return self.global_context[item]


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
        return

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


class Application:
    ctx = Context()

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


idx = Router()  # prefix根
# id1 = Router()  # prefix根
py = Router("/python")  # prefix  python
Application.register(idx)  # prefix注册,前缀应用名很少,所以这个是可以定义有限个很少..
Application.register(py)  # 路由对象已经注册到应用中的类属性了,


@idx.get("^/$")
def index(request: Request):  # index = idx.get("^/$")(index)   ->index = idx.route(self, rule, *method)(index)
    res = Response()
    res.status_code = 200
    # res.content_type="text/html"
    print(request)
    res.text = "<h1>luckynginx</h1>"
    return res


@idx.get("/{id:int}")
def index1(request: Request):  # index = idx.get("^/$")(index)   ->index = idx.route(self, rule, *method)(index)
    res = Response()
    res.status_code = 200
    # res.content_type="text/html"
    print(request)
    res.text = "<h1>luckynginx->{}</h1>".format(request.kwargs.id)
    return res


@idx.get("^/python$")
def index(request: Request):
    res = Response()
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
def show_python_product(request: Request):
    print(request)
    res = Response()
    res.text = "product no.{}".format(request.kwargs.product)
    return res


@Application.register_preintercepter
def show_headers(ctx: Context, request: Request):
    print(ctx.items())
    print(request.path, "Paaaaaaaaaaaaaaaath")
    print(request.user_agent, "agentttttttttttt")
    return request


@py.register_preintercepter
def show_prefix(ctx: Context, request: Request):
    print("_____________prefix = {}".format(ctx.router.prefix))
    return request


if __name__ == '__main__':
    from 数据库连接.仿orm框架.conpool import ConnPoo
    httpd = make_server('0.0.0.0', 8000, Application( ))
    try:
        pool = ConnPoo(3,"172.16.101.56","root","123456","test")
        with pool as cursor:
            with cursor:
                cursor.execute("select * from salaries")
                for i in cursor:
                    print(i)
        httpd.serve_forever()

    except KeyboardInterrupt:
        httpd.shutdown()
        httpd.server_close()
