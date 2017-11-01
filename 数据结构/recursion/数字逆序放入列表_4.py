def revert(num,target=[]):
    if len(num)!=0:
        target.append(num[-1:])
        revert(num[:len(num) - 1])   #return 的这个数值没有接   , 是因为   target 一直指向 同一块内存
    return target
print(revert(str(43267)))