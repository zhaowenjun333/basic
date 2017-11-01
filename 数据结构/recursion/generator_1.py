"""
规则:   可由生成器表达式 得到, 已经是生成器了,   不需要单独调用
        使用 yeild 得到   碰到 yield 返回 i, 暂停  ,  和 return 有区别,   需要 调用才可以
"""
def gen():
    print("line 1")
    yield 1

    print("line 2")
    yield 2


a =next(gen())           # 两个新的  生成器
print(a)
b  =next(gen())
print(b)


c=gen()    #同一个生成器
print(next(c))
print(next(c))
print(next(c,"end"))



