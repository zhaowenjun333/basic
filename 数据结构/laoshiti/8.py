keystr = input("input the key >>>")

if len( keystr)> 15  or len(keystr)< 10 :
    print("你输入的不是有效的密码,有效的范围在:{}~{}位之间".format(10,15))
else:
    d , a ,A,l=0,0,0,0
    for i in keystr:
        if i.isdecimal():
            d+=1
        if "a" < i and i <"z":
            a+=1
        if "A"< i and i<"Z" :
            A+=1
        if  "_" ==i:
            l+=1

    if d >0 and a >0 and A >0 and l >0:
        print("你输入的是有效的强密码√")
    else:
        print("你输入的不是有效的强密码×")
