num= str(input('>>'))
print("这个数是,",len(num),"位")
count = 0
for i in num:
    count+=1
    print("第" +str(count)+"位为:{1},出现次数为:{0}".format(num.count(i),i))