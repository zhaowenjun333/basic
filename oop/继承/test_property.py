class Animal:
    def __init__(self,name,ym,xm,age=1):
        self.name=name
        self._name=ym
        self.__name=xm
        self.__age=age
    def __shout(self):
        print("animal miaomiao")

class Cat(Animal):
    __gen="pppp"
    def __init__(self,name,ym,xm):
        self.name = name
        self._name = ym
        self.__name= xm
        Animal.__init__(self,name,ym,xm)   #  新式类 推荐使用     super.__init__(name,ym,xm)

    def getname(self):
        return self.__name

    def shout(self):
        # super.__shout()
        pass
    @property
    def getname1(self):
        return self.__name
cat =Cat("zhaoyun","sanwen","along")
print(cat.__dict__)
print(cat.name)
print(cat._name)
print(cat.getname())
print(cat.getname1)

print(Animal.__dict__)
print(Cat.__dict__)

an = Animal("zhishu","xioashu","shushu")
print(an.__dict__)