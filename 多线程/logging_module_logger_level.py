#!/usr/bin/env python3.6
# coding: utf-8
#logging_module.PY
# Created on 2017/12/14
# @author: zhaoyun
"""
description:
loggeing 的默认级别是 warning 30
 logger  是 logging模块的类
 level总结:
 A-> B ->C       每一个logger树都可以设置level 并向子类传递, 如果都没有设置就拿工厂的level ,工厂没有设置会采用默认是WARNING
"""
import logging   # 模块__init__

if __name__=="__main__":
    # format 格式 :   '%(@)s'   @ 可以为asctime message thread  funcName levelname levelno lineno module process thread processName threadName
    #工厂定义公共的属性:格式,日期格式,等级,文件输出的路径
    logging.basicConfig(format='%(asctime)s %(message)s %(thread)s %(school)s' ,level=23,datefmt='%Y-%m-%d %H:%M:%S',filename="C:\\Users\\zhaoyun\\PycharmProjects\\basic\\多线程\\test.log")
    # 第一个logger注意logger.name
    logger:logging.Logger  = logging.getLogger(__name__)    # 模块级别的  logger
    logger.setLevel(29)

    # 第二个logger注意logger.name 是前面name的   a.b
    logger1:logging.Logger  = logging.getLogger(__name__+".ok")    # 模块级别的  logger

    #日志输出
    logger.warning("dida.level is {}".format(logger.getEffectiveLevel()),extra={"school":"magedu"})
    logger1.warning("dida1.level is {}".format(logger1.getEffectiveLevel()),extra={"school":"magedu"})