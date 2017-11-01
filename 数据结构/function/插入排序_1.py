def insertsort( arrlst ):
    for i in range(1,len(arrlst)):
        j = i
        target=arrlst[i]
        while j>0 and  target<arrlst[j-1]:
            arrlst[j]=arrlst[j-1]
            j-=1
        arrlst[j ]=target
    print(arrlst)



insertsort([5,4,3,2,1,8,9,5,6])


def sort(lst):
    for i in range(1,len(lst)):
        j=i
        target = lst[j]
        while  j>0 and target <lst[j-1]:
            lst[j]=lst[j-1]
            j-=1
        lst[j]=target
    return lst
print(sort([5,4,3,2,1,8,9,5,6]))
