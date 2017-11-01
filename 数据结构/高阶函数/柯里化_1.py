
"""
柯里化
"""
def add1(x):
    def _add(y):
        return x+y
    return _add
# print(add1(4)(5))

