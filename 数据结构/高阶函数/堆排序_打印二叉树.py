origin=[300,20,80,409,50,10,60,90,70]
import  math
def treeprint(tree=[]):
    deep = math.ceil(math.log2(len(tree)))    #4
    count=1
    line=1
    width=deep**2-1   #   15
    for i in tree:
        count += 1
        print('{:^{}}'.format(i,2*width),end=" "*2)
        if count == 2**line :
            print()
            line+=1
            width = width // 2

treeprint(origin)
