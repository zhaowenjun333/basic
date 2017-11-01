import random
num = []
for _ in range(10):
    num.append(random.randint(1,20))

repeat = []
single = []
for i in num:
    if num.count(i) > 1:
        if i not in repeat:
            repeat.append(i)
    else:
        single.append(i)

print('repeat: {}, {}'.format(len(repeat), repeat))
print('single: {}, {}'.format(len(single), single))
