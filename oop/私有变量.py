class Person:
    def __init__(self,name,age=9):
        self.name=name
        self.__age=age

    def grorup(self,i):
        if  i >0 and i < 150:
            self.__age+=i

    def getage(self):
        return self.__age


p=Person('tom')
p.grorup(3)
print(p.getage())
print(p.__dict__)  #{'name': 'tom', '_Person__age': 12}