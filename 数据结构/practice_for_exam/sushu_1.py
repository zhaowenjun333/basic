import  math
lit=[2]
for  i  in range(3,100,2):
    for j in lit:
        if i%j ==0:
            break
    else:
        lit.append(i)
print(lit )