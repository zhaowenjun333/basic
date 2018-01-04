from .web import Luckynginx
import json


def jsonify(**kwargs):
    content = json.dumps(kwargs)
    body = "{}".format(content).encode()
    content_type = "application/json"
    return Luckynginx.Response(content,"200 ok",content_type=content_type,charset="utf-8")