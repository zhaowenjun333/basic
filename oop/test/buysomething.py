class ShoppingCar:
    def __init__(self):
        self.list=[]
    def addwood(self,wd):   #
        self.list.append(wd)
    def countmoney(self):
        return self.list
    def __len__(self):
        return len(self.list)
    def __iter__(self):
        newsc =ShoppingCar()
        newsc.list=[i  for i in  self.list]
        while newsc.list:
            yield newsc.list.pop()

    def __getitem__(self, item):
        return self.list[item]
    def __setitem__(self, key, value):
        self.list[key]=value
        return self

    def __add__(self, other):  # 链式编程    cart+3+2+9+3+5
        self.list.append(other)
        return self
class Wood:
    def __init__(self,name,price):
        self.name=name
        self.price=price
    def __str__(self):
        return str((self.name,self.price))
rice=Wood("redrice",19)
drink=Wood("erguotou",20)
sc=ShoppingCar()
sc.addwood(rice)
sc.addwood(drink)
m=0
for n in   sc.countmoney():
    m+=n.price
print("pay me ",m,"yuan")

print(len(sc))

for i in iter(sc):
    print(i )
print(sc[1])

sc[1]=3
print(sc[1])

sc +Wood("haizhilan","699")
print(sc)
print("{{{{{{{{{{")
for i in iter(sc):
    print(i )


class MyDict(dict):
    pass