class  Person:
    def __init__(self,name,age):
        self.name=name
        self.age=age
    def showage(self):
        print("{0.name} 'age is {0.age}".format(self))

tom = Person('tom',18)
jerry= Person('Je',20)

tom.showage()
jerry.showage()