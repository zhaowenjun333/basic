
setfreindcircle=[{1,2,3,4},{3,4,6},{23,23,5,6},{2,3,5,},{23,5,6,7,8,9}]
newpeople=set(input("请输入这个人的id"))  # 这个方法有问题
relation=set()
for i in setfreindcircle:
      if newpeople.issubset(i):
          relation.add(i)
if relation:
    print("新加入的这个人和其他人都不是好友")
else:
    print("这人有好友")