def findmax(x,y,*args):
    max = 0
    if x>y:
        max =x
    else:
        max=y
    if len(args)==0:
        return max
    else:
        for n in  args :
            if n>max:
                max =n
        return  max

print(findmax(2,23,4,5,6))


def nums(x,y,*args):
    print(min(x,y,*args))
    print(max(x,y,*args))





