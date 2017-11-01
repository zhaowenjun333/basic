import random

s1 = []
s2 = []
for _ in range(10):
    s1.append(random.randrange(10,21))
    s2.append(random.randrange(10,21))
print(s1)
print(s2)

a = set(s1)
b = set(s2)
print('The set a has {} difference nums'.format(len(a)))
print('The set b has {} difference nums'.format(len(b)))
print('total differ nums: {}'.format(len(a | b)))

c = a & b
print('same nums: {}'.format(c))
d = a | b - c
print('difference nums: {}'.format(d))
