#!/usr/bin/env python3
# coding: utf-8
# propertyjob.py
# Created on 2017/11/22
# @author: zhaoyun


class Property:
    def __init__(self, fget, fset=None, fdel=None, doc=''):
        self.fget, self.fset, self.fdel = fget, fset, fdel
        self.doc = doc

    def __get__(self, instance, owner):
        if instance is not None:
            return self.fget(instance)
        return self

    def __set__(self, instance, value):
        if not callable(self.fset):
            raise AttributeError('cannot set attr')
        self.fset(instance, value)

    def __delete__(self, instance):
        if not callable(self.fdel):
            raise AttributeError('cannot delete')
        self.fdel(instance)

    def setter(self, fset):
        self.fset = fset
        return self

    def deleter(self):
        self.fdel = fdel
        return self

class A:
    def __init__(self, data):
        self._data = data

    @Property       # data = Property(data)  Property类实例
    def data(self):
        return self._data

    @data.setter        # data = data.setter(data)(self, value)
    def data(self, value):
        self._data = value


a = A(5)
# print(vars(a))          # {'_data': 5}
print('-> ', a.data)
a.data = 15
print(a.data)
print(vars(a))