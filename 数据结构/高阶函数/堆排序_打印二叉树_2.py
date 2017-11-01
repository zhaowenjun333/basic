import  math
origin=[30,20,80,40,50,10,60,90,70]
def treeprint(tree):
    index=1
    depth=math.ceil(math.log2(len(tree)))
    sep=" "
    for i in range(depth):
        offset=2**i
        print(sep*(2**(depth-i-1)-1),end="")
        line=tree[index:index+offset]
        for j ,x in enumerate(line):
            print("{:>{}}".format(x,len(sep)),end="")
            interval= 0 if i==0 else 2**(depth-i)-1
            if j< len(line)-1:
                print(sep*interval,end="")
        index+=offset
        print()

treeprint(origin)
