"""
如果 实例变量没有的话,就会指向 类变量 ,类的属性改变了,实例变量也会变
但是一旦类变量赋值即定义  ,就会拥有自己的属性不再引用类的属性 ,类的属性改变了,实例变量也不会变

"""
class Person:
    age = 12
    def __init__(self,name):
        self.name=name
    @classmethod
    def changeage(cls,age):
        cls.age=age


tom = Person('tom')
jerry=Person("jerry")

print(tom.age,tom.name)
print(jerry.age,tom.name)
tom.age=29              # tom 拥有自己的年纪属性了  ,jerry没有
Person.age=20
print(tom.age,tom.name)
print(jerry.age,jerry.name)     # 当 类变量改变后,tom 拥有自己的年纪属性了 所以age没有改变 ,jerry没有age 所以跟着 类变量age改变

# 实例变量改变后   ,就不在共享 类变量    ^^^^^^^^^^^下面是 举证
jerry.age=40
print(Person.age)
print(jerry.age,jerry.name)

# 下面是测试特殊属性
print(tom.__class__.__name__)    #  实例没有 __name__  属性
print(tom.__dict__)
print(sorted(tom.__dict__.items()))
print(Person.__dict__)
Person.changeage(23)
print(Person.age)