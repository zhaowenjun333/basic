print(max(range(10)))

print(max(2,3,*(3,6)))
def nums(x,y,*args):
    print(args)
    print(min(x,y,*args))
    print(max(x,y,*args))


nums(2,5,6,7)
nums(2,3,*(3,4,5,6))