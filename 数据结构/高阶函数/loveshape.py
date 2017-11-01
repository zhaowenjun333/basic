
#!/usr/bin/env python3
# coding: utf-8
# file name: untitled_grid.py
# contact: fz420@qq.com
# created by lite at 2017/9/19 11:11

grid =[
    [' ', ' ', ' ', ' ', ' ', ' '],
    [' ', '❤', '❤', ' ', ' ', ' '],
    ['', '❤', '❤', '❤', ' ', ' '],
    ['❤', '❤', '❤', '❤', '❤', ' '],
    [' ', '❤', '❤', '❤', '❤', '❤'],
    ['❤', '❤', '❤', '❤', '❤', ' '],
    ['❤', '❤', '❤', '❤', ' ', ' '],
    [' ', '❤', '❤', ' ', ' ', ' '],
    [' ', ' ', ' ', ' ', ' ', ' ']
]

# ❤ grid = [[1, 3, 5, 7], [4, 6, 8, 10]]
# 提示：
# 你可以认为 grid[x][y]是一幅“图”在 x、y 坐标处的字符，该图由文本字符组
# 成。原点(0, 0)在左上角，向右 x 坐标增加，向下 y 坐标增加。

row = len(grid)
column = len(grid[0])
new = [[ 0 for col in range(len(grid))] for i in range(len(grid[0]))]

for i in range(column):
    for j in range(row):
        new[i][j] = grid[j][i]

# for i in grid:
#     print(i)
# print(' ' * 30)
#
for k in new:
    print(k)