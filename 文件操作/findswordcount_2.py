"""
有一个文件,对其进行单词统计,并显示单词重复的最多的10个单词

"""
f=open(file="sample.txt",encoding="utf-8",mode="r+t")    #
# print(f.read())
lista=[]
while True:
    line=f.readline()
    if not line:
        break
    lista.extend( [ i for i in line[:-1].split(" ") if (i !='' and i !="»" and i!='|')  ] )
m=len(lista)
dickdata= dict(zip(lista ,(1,)*m ))
for i in lista:
    dickdata[i]=dickdata[i]+1
# print(dickdata)
lst=sorted(dickdata.items(),key=lambda x:x[1],reverse=True)
print(lst)
# a =sorted(dickdata.values())
# print(a)
# for i,v in dickdata.items():
#     alist[i] +=1
# count =0
# for i  in alist:
#     if i !=0:
#         print("{} has appear {} times".format(count,i))
#     count+=1
# print(len(lista))
# f.close()
