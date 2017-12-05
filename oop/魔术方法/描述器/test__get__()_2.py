#!/usr/bin/env python3.6
# coding: utf-8
#test__get__().PY
# Created on 2017/11/26
# @author: zhaoyun
class A :

    def __init__(self):
        self.name ="bingo"
        print("A() is init")
    def __get__(self, instance, owner):          # 在这个例子中,owner 是  class B
        print(self,instance,owner)
        return self
class B :
    x = A()
    def __init__(self):
        print("B() is init")
    def __getattribute__(self, item):
        return self
print(B.__dict__)
print(B.x)                            # 这个三个调用都会
# print(B().x.name)                 #和反射一样,只要访问了类属性(这个属性指向另外一个对象实例),就会调用类属性的类的__get__() 方法
# print(B().x)                 #和反射一样,只要访问了类属性,就会调用类属性的__get__() 方法
