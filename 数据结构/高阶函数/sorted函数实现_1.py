
"""
sorted函数的实现
"""

def  sorted(lst,reverse=False):
    ret=[]
    for x in lst:
        for i,y in enumerate(ret):
            flag=x>y if reverse else x<y
            if flag:
                ret.insert(i,x)
                break
        else:
            ret.append(x)
        # for i in range(len(lst)):
        #     if lst[i]> x:
        #         ret.append()

    return ret

lst= [1,5,3]
print(sorted(lst))
