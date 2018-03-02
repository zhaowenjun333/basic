#!/usr/bin/env python3.6
# coding: utf-8
# test_connect.PY
# Created on 2018/1/8
# @author: zhaoyun
"""
description:

"""
import sqlalchemy
from sqlalchemy import create_engine
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import Column, Integer, String

print(sqlalchemy.__version__)

USERNAME="root"
PASSWD="123456"
IP="172.16.101.67"
PORT=3306
DBNAME="test"
PARAMS ="charset=utf8"
URL="mysql+pymysql://{}:{}@{}:{}/{}?{}".format(USERNAME,PASSWD,
                                               IP,PORT,DBNAME,PARAMS)
engine=create_engine( URL, echo=True)

Base = declarative_base()


class User(Base):
    __tablename__ = 'user'
    id = Column(Integer, primary_key=True,autoincrement=True)
    name = Column(String(3))
    fullname = Column(String(64))
    password = Column(String(128))

Base.metadata.create_all(engine)
#
# CREATE TABLE user (
#     id INTEGER NOT NULL AUTO_INCREMENT,
#                         name VARCHAR(3),
#                         fullname VARCHAR(64),
#                         password VARCHAR(128),
#                         PRIMARY KEY (id)
# )
#  生产中从，开发库直接导出sql，但程序员可以通过这个方法新建一张表

#千里之穴，溃于蚁穴
