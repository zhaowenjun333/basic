
# alist=[2,4,5,6,]
# print(alist.pop())
# print(alist.pop(2))
# print(alist.pop(-1))
import datetime
import time
now = datetime.datetime.now()
blist= list()
clist=[]
clist.extend([1,2,6])
print(clist)
clist.insert(1,3)     #第一个参数是位置 下标 ,第二个是  数值
print(clist)
time.sleep(2)
stop = datetime.datetime.now()-now
cap=stop.total_seconds()
print(cap)

