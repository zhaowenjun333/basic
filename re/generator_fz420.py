import random,time
def source():
    while True:
        yield {'value': random.randint(1, 10)}
        time.sleep(1)
        print('inner source func')

gener= source()
for i in range(10):
    print(next(gener))
