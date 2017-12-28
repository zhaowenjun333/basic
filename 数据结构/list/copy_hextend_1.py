import copy
l=["123123",2,3,4,5]
s=copy.copy(l)
d=copy.deepcopy(l)
print("l:",l,id(l),"*1*","d:",d,id(d),"*1*","s:",s,id(s))
l[1]="x" #为什么这里shallow copy不跟着变呢？
print("l:",l,id(l),"*2*","d:",d,id(d),"*2*","s:",s,id(s))
# l[0][0]="55" #为什么这里shallow copy跟着变呢，是不是shallow copy只更新“二级嵌套“的变化？
print("l:",l,id(l),"*3*","d:",d,id(d),"*3*","s:",s,id(s))

l[0][0]=""  # 报错的原因是字符串无法修改
print(l[0][0])