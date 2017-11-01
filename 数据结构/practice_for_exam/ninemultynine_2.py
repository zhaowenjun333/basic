for i in range(1,10):
    print(" "*7*(i-1),end="")
    for j in range(i ,10 ):
        product =i*j
        if product<10 :
            end='  '
        else:
            end=" "
        print("{}*{}={}".format(j,i,i*j),end=end)
    print()