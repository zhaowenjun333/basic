
class Person:
    def __init__(self,chinese,eng,history):
        self._chinese=chinese
        self._eng=eng
        self._history=history
    @property
    def getchinese(self):
        return self._chinese
    @getchinese.setter
    def setchinese(self,chinese):
        self._chinese=chinese
    @getchinese.deleter
    def delechinese(self):
        del self._chinese
    def __del__(self):
        print("game over")

p1 =Person(67,89,45)
print(p1.getchinese)
p1.setchinese=999
print(p1.getchinese)
p1.delechinese
print(p1.getchinese)
