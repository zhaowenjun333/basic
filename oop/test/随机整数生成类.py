"""
随机整数生产类,可以指定个数, 可以指定数值范围,没有指定就是默认值
使用类生成 20个数字 .两两配对形成二维坐标 .把这些坐标组织起来打印
"""
class CreateNumbers:
    @classmethod                 #
    def getnums(cls,size=20,start=0,end=100):
        listnums=[]
        import random
        for i in range(size):
            listnums.append(random.randint(start, end+1))
        return listnums

class point:
    def __init__(self,x,y):
        self.x=x
        self.y=y
    def show(self):
        print(self.x,self.y)

result=CreateNumbers.getnums(10,2,9)
for i in  range(len(result)//2):
    point(result[2*i],result[2*i+1]).show()

#其他指定个数,起始 情况
# print(CreateNumbers.getnums(30))
# print(CreateNumbers.getnums(10,2,9))