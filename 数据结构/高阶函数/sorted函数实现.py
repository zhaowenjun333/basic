"""
sorted函数的实现
"""


def sorted(lst, reverse=False):
    ret = []
    counter = 0
    for x in lst:
        counter += 1
        j = counter
        target = x
        while j > 0 and target < lst[j - 1]:
            lst[j] = lst[j - 1]
            j -= 1
        ret.append(target)

        # for i in range(len(lst)):
        #     if lst[i]> x:
        #         ret.append()

    return ret


lst = [1, 5, 3]
print(sorted(lst))
