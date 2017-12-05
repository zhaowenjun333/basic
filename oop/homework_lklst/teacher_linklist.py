#!/usr/bin/env python3.6
# coding: utf-8
#teacher_linklist.PY
# Created on 2017/11/26
# @author: zhaoyun
"""
description:

"""
class SingleNode:
    def __init__(self,item,next=None):
        self.item=item
        self.next = next

    def __repr__(self):
        return repr(self.item)

class LinkList:
    def __init__(self):
        self.head=None
        self.tail= None
        self.size = 0

    def add(self,item):
        node = SingleNode(item)
        if self.head is None:
            self.head = node
        else:
            self.tail.next = node
        self.tail= node

    def iternode(self):
        current = self.head
        while current :
            yield current
            current = current.next

ll = LinkList()
ll.add("abc")
ll.add("123")
print(ll.head)
print(ll.tail)
for x in ll.iternode():
    print(x)