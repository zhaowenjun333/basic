import  random
random.shuffle("")
a = [  str(i)+'.'  for i  in  range(100) ]
print(a )

for i in range(100):
    print("{0:>0004}.{1}".format(i,"".join(random.choice("qbcd") for  _  in range(10))))
