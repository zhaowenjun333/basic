#!/usr/bin/env python3.6
# coding: utf-8
#demoforlogger.PY
# Created on 2018/4/11
# @author: zhaoyun
"""
description:

"""
import  logging
logging.basicConfig(level=logging.INFO)
logging.info(222)
logging1 =logging.getLogger(__name__)
h1 = logging.FileHandler(filename="log.log")
h1.setFormatter(logging.Formatter("%(status)s %(message)s %(asctime)s"))
logging1.addHandler(h1)
logging1.info("{}".format("sdfsdf"),extra = {"status":1})
# 18251972037