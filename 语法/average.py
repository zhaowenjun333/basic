count=0
sum=0
while True:
    count+=1
    numa=int(input( "请输入第"+str(count)+"个数字："))
    sum+=numa
    print("你输入了"+str(count)+"个数，他们的平均数为",sum/count)
    flag = input("请输入n 退出，y继续：")
    if flag == "y":
        continue
    else:
        print("成功推出")
        break
