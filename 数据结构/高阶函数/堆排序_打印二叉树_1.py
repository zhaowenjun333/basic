origin=[30,20,80,40,50,10,60,90,70]
import  math
def treeprint(tree,unit_width=2):
    deep = math.ceil(math.log2(len(tree)))
    length=len(tree)
    index=0
    width=2**deep-1
    for i in range(deep):
        for j in range(2**i):
            # print("{:{}}".format(tree[index] , width * unit_width ),end=" "*unit_width)
            print("{:^{}}".format(tree[index] , width * unit_width ),end=" " )
            index +=1
            if index>=length:
                break
        width=width//2
        print()
treeprint(origin)
