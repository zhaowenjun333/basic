for i in range(5):#变量i控制行数
    for j in range(5 - i):#(1,rows-i)
        print(" ",end="")
        j += 1
    for k in range(2 * i - 1):#(1,2*i)
        if k == 0 or k == 2 * i - 2:
            print("*",end="")
        else:
            print(" ",end="")
        k += 1
    print("\n")
    i += 1