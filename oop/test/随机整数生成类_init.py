"""
随机整数生产类,可以指定个数, 可以指定数值范围,没有指定就是默认值
使用类生成 20个数字 .两两配对形成二维坐标 .把这些坐标组织起来打印




"""
import random
class CreateNumbers:
    def __init__(self,count=10,start=1,stop=10):
        self.count=count
        self.start=start
        self.stop=stop
        self._gen=self._generate()
    def _generate(self):
        while True:
            yield [   random.randint(self.start,self.stop)  for _ in range(self.count)]
    def generate(self,count ):
        self.count=count
        return  next(self._gen)

    @classmethod                 #
    def getnums(cls,size=20,start=0,end=100):
        listnums=[]
        import random
        for i in range(size):
            listnums.append(random.randint(start, end+1))
        return listnums

rg=CreateNumbers()
lst =rg.generate(4)
print(lst)