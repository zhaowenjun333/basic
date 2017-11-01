2
"pwogpwj"
[2,3,5,6]
def revert(n,lst=[]):
    # if lst is None:
    #     lst=[]
    x,y =divmod(n,10)
    lst.append(y)
    if x==0:
        return lst
    return revert(x,lst)


a =revert(1245)
print(a)