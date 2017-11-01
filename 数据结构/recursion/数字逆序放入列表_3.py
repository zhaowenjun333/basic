def revert(num,target=[]):
    if num:
        # target.append(num[len(num)-1])       #target.append(num[-1:])
        target.append(num[-1:])              # target.append(num[len(num)-1])
        a =revert(num[:len(num)-1])
    return target

print(revert(str(43267)))