#!/usr/bin/env python3.6
# coding: utf-8
#logging_module.PY
# Created on 2017/12/14
# @author: zhaoyun
"""
description:

loggeing 的默认级别是 warning 30


方法可以为: debug  info warning error critical
"""
import logging

if __name__=="__main__":
    # format 格式 :   '%(@)s'   @ 可以为asctime message thread  funcName levelname levelno lineno module process thread processName threadName
    logging.basicConfig(format='%(asctime)s %(message)s %(thread)s %(school)s' ,level=23,datefmt='%Y-%m-%d %H:%M:%S',filename="C:\\Users\\zhaoyun\\PycharmProjects\\basic\\多线程\\test.log")
    # 额外参数
    logging.warning(msg="hai",extra={"school":"magedu"})
    #c 风格
    logging.warning( "i am %s , %d","hasdfsdfi",34,extra={"school":"magedu"} )
