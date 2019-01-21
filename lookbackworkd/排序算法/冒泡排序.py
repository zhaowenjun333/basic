#!/usr/bin/env python3.6
# coding: utf-8
#快速排序.PY
# Created on 2017/12/21
# @author: zhaoyun
"""
description:

"""
# lst=[1,9,8,5,6,7,4,3,2]
# length =len(lst)
# for i in range(length):
#     count=0
#     for j in range(length-1 -i):
#         if lst[j] > lst[j+1]:
#             lst[j],lst[j+1] =lst[j+1],lst[j]
#             count +=1
#     if count ==0:
#         break
# print(lst)
lst = [6,9,8,5,1,7,4,3,2]
length = len(lst)
for i in range(length): # 0-8
    for j in range(length-1-i):  #7
        if lst[j]> lst[j+1]:
            lst[j],lst[j+1]=lst[j+1], lst[j]
print(lst)