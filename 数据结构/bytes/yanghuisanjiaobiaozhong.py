n= int(input("please input the line: "))
k=1
while True:
    k= int(input("please input the num  :"))
    if k > n:
        print("attention!!!!num must < line,try again please ,",end="")
        continue
    else:
        break
oldline=[]
newline=[1]
length=0
for i in range(1,n):
    oldline=newline.copy()
    oldline.append(0)
    newline.clear()
    offset=0
    while offset<=i:
        newline.append(oldline[offset-1]+oldline[offset])
        offset+=1
print(newline)
print("the anwser is :{}".format(newline[k-1]))

