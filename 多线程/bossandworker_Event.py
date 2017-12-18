#!/usr/bin/env python3.6
# coding: utf-8
#bossandworker.PY
# Created on 2017/12/15
# @author: zhaoyun
"""
description:

"""
from threading import  Event,Thread
import logging,time
logging.basicConfig(level=logging.INFO)
def boss(makecupsevent:Event,paymoneyevent:Event):
    logging.info("I'm boss ,waiting for U")
    makecupsevent.wait()
    logging.info("god is blessing U,work is finished")
    paymoneyevent.set()
def worker(makecupsevent:Event,paymoneyevent):
    countcup = 0
    while True:
        countcup+=1
        timelostofmakuupcups_name()
        logging.info("worker have made {} cups".format(countcup))
        if countcup==10:
            makecupsevent.set()
            break
    logging.info("i finished my makingcup jobs ,payme money!")
    paymoneyevent.wait()
    logging.info("i have recept ten yuan money ths")
def timelostofmakuupcups_name():
    time.sleep(1)
makecupsevent = Event()
paymoneyevent = Event()
w = Thread(target=worker,args=(makecupsevent,paymoneyevent))
b = Thread(target=boss,args=(makecupsevent,paymoneyevent))
w.start()
b.start()