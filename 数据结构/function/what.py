"""猴子吃桃     规律            当天的总数计算：
day1 ：      t/2-1           t1 = (t2 +1)*2
day2:        t/2-1           t2 = ( t3 +1)*2
day3：       t/2-1           t3 = (t4 +1)*2
  ..           ..                  ..
 day10：       t/2-1           1
所有天数的桃子数：  t1+t2+t3
"""


# 递归做法：

def get(n):
    if n == 1:
        return 1
    else:
        return (get(n - 1) + 1) * 2
print(get(10))
print(get(n=10))


print(get)
print(callable(get))