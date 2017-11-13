
class Person:
    def __init__(self,chinese,eng,history):
        self._chinese=chinese
        self._eng=eng
        self._history=history
    @property   #  属性装饰器       ,只有私有属性才会有这个getter setter 方法
    def chinese(self):
        return self._chinese
    @chinese.setter
    def chinese(self,chinese):
        self._chinese=chinese
    @chinese.deleter
    def chinese(self):
        del self._chinese
    def __del__(self):
        print("game over")

p1 =Person(67,89,45)
print(p1.chinese)
p1.setchinese=999
print(p1.chinese)
del p1.chinese
# print(p1.chinese)

