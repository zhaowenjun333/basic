m=""
while True:
    m=input('>>').strip().lstrip("0")
    if  m.isdigit() and len(m)==5 :
        break
    else:
        print("wrong input ,请重新输入,规则是5位数字")
print("jklslgl",m)
alist =[]
count=0
for i in m:
    alist.append(i)

num=len(alist)
for  j in range(1,num):
    for i in range(1,num -j +1):    #这一步是关键
        if alist[i-1] > alist[i]:
            alist[i-1], alist[i] = alist[i], alist[i-1]
print(alist)