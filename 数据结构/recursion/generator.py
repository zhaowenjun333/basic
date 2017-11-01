"""
规则:   可由生成器表达式 得到
        使用 yeild 得到   碰到 yield 返回 i, 暂停
"""
def inc():
    for i in range(5):
        yield i
print(type(inc()))
x = inc()
for m in x :
    print(m,"*")

y = (i for i in range(2))
for j in y :
    print(j,"**")