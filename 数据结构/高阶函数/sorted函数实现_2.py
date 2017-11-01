"""
sorted函数的实现
"""
def comp(a, b,reverse):
    flag = a > b if reverse else a < b
    return flag
def  sorted(lst,reverse=False):
    ret=[]
    for x in lst:
        for i,y in enumerate(ret):
            if comp(x,y,):
                ret.insert(i,x)
                break
        else:
            ret.append(x)
    return ret

lst= [1,5,3]
print(sorted(lst))