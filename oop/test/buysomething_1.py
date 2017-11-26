class ShoppingCar:
    def __init__(self):
        self.list=[]
    def addwood(self,wd):
        self.list.append(wd)
    def countmoney(self):
        return self.list

class Wood:
    def __init__(self,name,price):
        self.name=name
        self.price=price
rice=Wood("redrice",19)
drink=Wood("erguotou",20)
sc=ShoppingCar()
sc.addwood(rice)
sc.addwood(drink)
m=0
for n in   sc.countmoney():
    m+=n.price
print("pay me ",m,"yuan")