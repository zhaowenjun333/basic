#!/usr/bin/env python3
# coding: utf-8
# fib.py
# Created on 2017/11/15
# @author: lite 


# 1, 1, 2, 3, 5, 8, 13, 21

class Fib:
    def __init__(self):
        self.__value = [1, 1]
        # self.length = len(self.__value)
    def __call__(self, n):
        if n < 0:
            return
        elif n < len(self.__value):

            return self.__value[n-1]
        else:
            for _ in range(len(self.__value),n):
                self.__value.append(self.__value[-1] + self.__value[-2])
            # self.length=len(self.__value)
            return self.__value[n-1]

    # def __getitem__(self, index):
    #     if index < 0:
    #         return
    #     elif index <= len(self.__value):
    #         return self.__value[index-1]
    #     else:
    #         return self.__call__(index)

    # def __iter__(self):
    #     return iter(self.__value)

    @property
    def value(self):
        return self.__value


fib = Fib()
# print(fib(10))
# # print(fib(15))
print(fib(10))
print(fib(20))
print(fib.value)
print(len(fib.value))

# for i in fib:
#     print(i)


