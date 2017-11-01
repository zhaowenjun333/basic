# 字符串不可变,   下面两个 说明 字符串不可变   ,相加重新分配地址 ,而不是 原地修改
a = "kdd;h"
print(id(a))

a= a+'温哥华然后'
print(id(a))
print(a)

"""
注释吗
"""

b = """ hahha  """
print( b)
'''
字符和字符串
字符和字节  
一个英文字符占位一个字节   ,
unicode 中 ,一个中文字符占3个字节    initialize
一个字节 byte 占位8bit 位
中文字符 和 英文字符
'''
c = 'c'
print(c)


