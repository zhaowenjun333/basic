n =0
sum=0
while True:
    i= input(">>>")
    if i =="quit":
        break
    n+=1
    sum+=int(i)
    avg=sum/n
    print(avg)

