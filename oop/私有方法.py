class Person:
    __CLASSROOM='magedu'              #  类的私有属性(变量)
    def __init__(self,name,age=9):
        self.name=name
        self.__age=age

    def getmoney(self):
        print("{} gives money ....".format(self))
    def __getmoney1(self):                      #  类的私有方法
        print("{} gives money ....".format(self))

p1=Person('lisi')
# print(p1.__CLASSROOM)     # 类的私有属性   对象无法访问
print(p1.getmoney())
# print(p1.__getmoney1())    # 类的私有方法   对象无法访问
# print(Person.__getmoney1())    # 类的私有方法   对象无法访问