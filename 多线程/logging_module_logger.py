#!/usr/bin/env python3.6
# coding: utf-8
#logging_module.PY
# Created on 2017/12/14
# @author: zhaoyun
"""
description:

 logger  是 logging模块的类
"""
import logging   # 模块__init__

if __name__=="__main__":
    # format 格式 :   '%(@)s'   @ 可以为asctime message thread  funcName levelname levelno lineno module process thread processName threadName
    #工厂
    logging.basicConfig(format='%(asctime)s %(message)s %(thread)s %(school)s' ,level=23,datefmt='%Y-%m-%d %H:%M:%S',filename="C:\\Users\\zhaoyun\\PycharmProjects\\basic\\多线程\\test.log")
    # 第一个logger注意logger.name
    logger:logging.Logger  = logging.getLogger(__name__)    # 模块级别的  logger
    print(logger.name,"eeeeeparentid->",logger.parent,id(logger.parent),"eee",type(logger),"eeeeeeee",logger,"eeee",id(logger))
    # 第二个logger注意logger.name 是前面name的   a.b
    logger1:logging.Logger  = logging.getLogger(__name__+".ok")    # 模块级别的  logger
    print(logger1.name,"eeeeeparentid->",logger1.parent,id(logger1.parent),"eee",type(logger1),"eeeeeeee",logger1,"eeee",id(logger1))


