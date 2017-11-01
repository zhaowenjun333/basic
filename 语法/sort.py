numa=int(input( "请输入第一个数字："))
numb=int(input("请输入第二个数字："))

if numa<numb:
    tempc=numa
    numa = numb
    numb=tempc
print("从小到大",numb,numa)