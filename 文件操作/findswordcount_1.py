"""
有一个文件,对其进行单词统计,并显示单词重复的最多的10个单词

"""
# f=open(file="sample.txt",encoding="utf-8",mode="r+t")    #
# print(f.read())

#!/usr/bin/env python3
# coding: utf-8
# file name: untitled_word.py
# contact: fz420@qq.com
# created by lite at 2017/10/23 20:22



def count_word(file:str, top=15, reverse=True) ->list:
    symbol = '!"#$%&\'()*+,./:;<=>?@[\\]^_`{|}~\n'
    table = str.maketrans(symbol, len(symbol) * ' ')
    count_dcit = {}

    with open(file, encoding='utf8') as f:
        w = f.read().translate(table).split(' ')

        for i in w:
            if i.isalpha():
                i = i.lower()
                count_dcit[i] = count_dcit.setdefault(i, 0) + 1

    return sorted([(v, k) for k, v in count_dcit.items()], reverse=reverse)[:top]



# print(count_word('sample.txt'))


for x, y in count_word('sample.txt'):
    print('{:15}{:10}'.format(y, x))

