a,b = 1,2
a,b = (1,2)
a,b = [1,2]
a,b = [10,20]
a,b = {10,20}
a,b = {'a':10,'b':20} # 􀮳􀦿􀛅􀧈􀟣􀐭􀕕􀑙􀪆􀟣
a,b,c = {10,20,30}
a,*b = {10,20,30}
z=[a,b] = (1,2)
z=[a,b] = 10,20
z=(a,b) = {30,40}


lst = list(range(10))
_,second,_,four,*_,lastsecond,_=lst
print(second)
print(four)
print(lastsecond)



lst=[1,(2,3,4),5]
_,(*_,target),_=lst   #

_,[*_,target],_=lst
_,[*_,target],_=lst   #  之所以不能使用   集合是因为集合 可以去重
print(target)