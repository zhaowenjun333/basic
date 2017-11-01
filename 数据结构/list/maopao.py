

import  random
alist = list(range(1,10))
random.shuffle(alist)
print("排序前:",alist)
alist=[1,2,4,3,5,6,7,8,9]
nums=alist

length=len(nums)
count_swap=0
count=0
for i in range(length):
    flag=False
    for j in range(length-i-1):
        count+=1
        if nums[j] > nums[j+1]:
            tmp=nums[j]
            nums[j] =nums[j+1]
            nums[j+1]=tmp
            flag=True
            count_swap +=1
    if not  flag:
        break
print(nums,count_swap,count)

