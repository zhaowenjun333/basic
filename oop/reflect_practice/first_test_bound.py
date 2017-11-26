
#!/usr/bin/env python3.6
# coding: utf-8
#first.PY
# Created on 2017/11/17
# @author: zhaoyun
class A:
    def __init__(self,x):
        self.x=x

    def foo(self):
        print("iam foo method")
a = A(3)
print(a.foo)
print(A.foo)