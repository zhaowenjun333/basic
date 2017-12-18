#!/usr/bin/env python3.6
# coding: utf-8
#logging_module.PY
# Created on 2017/12/14
# @author: zhaoyun
"""
description:
loggeing 的默认级别是 warning 30
 logger  是 logging模块的类

"""
import logging   # 模块__init__
import sys
if __name__=="__main__":
    # format 格式 :   '%(@)s'   @ 可以为asctime message thread  funcName levelname levelno lineno module process thread processName threadName
    #工厂定义公共的属性:格式,日期格式,等级,文件输出的路径
    logging.basicConfig(format='%(asctime)s %(message)s %(thread)s %(school)s' ,level=23,datefmt='%Y-%m-%d %H:%M:%S',filename="C:\\Users\\zhaoyun\\PycharmProjects\\basic\\多线程\\test.log")
    # 第一个logger注意logger.name
    logger:logging.Logger  = logging.getLogger(__name__)    # 模块级别的  logger
    # logger.setLevel(29)
    logger.addHandler(logging.StreamHandler(stream = sys.stdout ))    #  stream 可以为: sys.stderr

    # 第二个logger注意logger.name 是前面name的   a.b
    logger1:logging.Logger  = logging.getLogger(__name__+".ok")    #
    logger1.addHandler(logging.StreamHandler(stream=sys.stderr))  # stream 可以为: sys.stderr

    # 第三个logger注意logger.name 是前面name的   a.b.c
    logger2: logging.Logger = logging.getLogger(__name__ + ".ok"+".c")  #
    handler= logging.StreamHandler(stream=sys.stderr)
    handler.setLevel(40)       #
    logger2.addHandler( handler )  # stream 可以为: sys.stderr

    #日志输出
    logger.warning("logger.level is {}".format(logger.getEffectiveLevel()),extra={"school":"magedu"})
    logger1.warning("logger1.level is {}".format(logger1.getEffectiveLevel()),extra={"school":"magedu"})
    logger2.warning("logger2.level is {}".format(logger2.getEffectiveLevel()), extra={"school": "magedu"})