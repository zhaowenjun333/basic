import  random
for i in range(100):
    print("{0:>0004}.{1}".format(i, ''+(random.choice("qbcd") for _  in range(10))))

