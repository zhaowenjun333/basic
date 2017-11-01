n=6
oldline=[]
newline=[1]
length=0
# print(newline)
for i in range(1,n):
    oldline=newline.copy()
    oldline.append(0)
    newline.clear()
    offset=0
    while offset<=i:
        newline.append(oldline[offset-1]+oldline[offset])
        offset+=1
print(newline)





