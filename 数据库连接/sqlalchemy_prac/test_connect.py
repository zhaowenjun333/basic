#!/usr/bin/env python3.6
# coding: utf-8
#test_connect.PY
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
engine= create_engine("mariadb://root:123456@172.16.101.67/test",
                      encoding='utf-8', echo=True)

Base = declarative_base()


class User(Base):
    __tablename__ = 'users'
    id = Column(Integer, primary_key=True)
    name = Column(String)
    fullname = Column(String)
    password = Column(String)

Base.metadata.create_all(engine)