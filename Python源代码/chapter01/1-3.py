#!/usr/bin/env python
# -*- coding: utf-8 -*-
import random
a = [56, 2, 1, 893, -0.4]						#列表类型
m =a.reverse()
n=sorted(a)

print(a)
print(m)
print(n)
random.shuffle(a)

b = len(a)									#结果为 5
b = max(a)									#结果为 893
b = min(a)									#结果为 -0.4
print((list(reversed(a))))	

print((sorted(a)))

