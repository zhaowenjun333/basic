
import random
class IntNum:
    def __init__(self, start=1, end=500, length=5):
        self.start = start
        self.end = end
        self.length = length
        self.gen = self._mkint()

    def _mkint(self):
         while True:    #   if self.start< self.end      导致了生成器只有一次循环
            yield [random.randint(self.start, self.end) for x in range(self.length)]

    def mkint(self, length):
        self.length = length
        return next(self.gen)

class Point:
    def __init__(self, x, y):
        self.x, self.y = x, y

    def __str__(self):
        return "{}-{}".format(self.x, self.y)

    def __repr__(self):
        return "{}:{}".format(self.x, self.y)

x = IntNum(1, 100)
y = IntNum(11, 200)
print([Point(*v) for v in zip(x.mkint(6), y.mkint(6))])
print('--' * 30)
for i in zip(x.mkint(7), y.mkint(7)):
    print( Point(*i))

