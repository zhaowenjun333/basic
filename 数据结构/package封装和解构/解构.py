# {a,b}=1,1     # 这个矛盾出来了 , 集合里面是不可以重复的, 后面很有可能是重复的所以不支持这样的定义
# print(a)
# print(b)

a1,*b1= list(range(10))
print(a1,b1)

# *a2 = [1,2,4]  单独使用没有意义  这样相当于直接赋值

# *a3,*b3,c3=2,3,4     # 不支持两个 无法区分给谁
# print(a3,b3)


head ,*mid,tail="lajkglag"
print("{} -{} -{}".format(head,mid,tail))

_,*_,tail= enumerate(range(10))
print(tail)
print(_)
