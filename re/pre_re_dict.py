
"""
字典方法 setdefault(k [,default])
如果有对应的value 则返回value
如果没有则返回默认值


根据python的特性返回值可以是一个普通的数值也可以是一个对象[]  这样就可以调用了
"""


lst=[('yellow', 1), ('blue', 2), ('yellow', 3), ('blue', 4), ('red', 1)]
g={}
for k,v in lst:
    g.setdefault(k,[]).append(v)      # key:yellow 的value 不存在所以返回的是[] 然后调用[].append()   ,如果有的话就会[1].append(3)   -> 结果是[1,3]
print(g)