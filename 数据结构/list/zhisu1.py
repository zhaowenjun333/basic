
num=100
lst=[2]
for num in range(3,num+1,2):
    for i in lst:    # 可以替换为 range(3,int(num**0.5)+1,2): 或者 math.ceil(math.sqrt(num ))
        if num%i==0:
            break
    else:
        lst.append(num)
print(lst)


