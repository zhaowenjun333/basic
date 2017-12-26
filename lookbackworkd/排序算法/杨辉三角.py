#!/usr/bin/env python3.6
# coding: utf-8
#杨辉三角.PY
# Created on 2017/12/24
# @author: zhaoyun
"""
description:

"""
# [[1],[1,1],[1,2,1],[1,2,3,2,1]]
lst = []
for i  in range(0,5):
    if i ==0:
        lst.append([1])
    else:
        pre=lst[i-1]
        cur=[1]
        for j in range(len(lst)-1):
            temp = pre[j] +pre[j+1]
            cur.append(temp)
        cur.append(1)
        lst.append(cur)
print(lst)