def showconfig(username,password,**kwargs):
    print(username)
    print(password)
    print(kwargs)

showconfig(2,3,a=3,b=4)

print("__________________________")
def showconfig(username,*args,**kwargs):
    print(username)
    print(args)
    print(kwargs)
showconfig(2,3,4,a=3,b=4)

print("__________________________")

"""

def showconfig(**kwargs,*args):      #showconfig(a=6,7)      违反了规则 实参调用不能这样调用,为难实参
    print(args)
    print(kwargs)

def showconfig(**kwargs,username):    # 截获 关键字
    print(username)
    print(args)
    print(kwargs)
"""
