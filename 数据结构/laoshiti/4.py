import datetime
import random
for i in range(20):
    strp = ""
    strp += str(datetime.datetime.now().timestamp()).split(".")[0]

    numstr=""
    for n in range(3):
            numstr+=str(random.choice(range(10)))
    strp = strp+"_"+numstr

    alpha =""
    for a in range(8):
         alpha +=chr(random.randrange(97,123))
    strp=strp+"_"+alpha

    print(strp)
