#每行先置为1 ,然后  替换里面的1          row[j]不用重新加入了

triangle=[]
n=6
for i in range(1,n+1):
    row=[1]*i
    triangle.append(row)
print(triangle)
