class Person:
    age = 18
    def __init__(self,age =19):
        self.age=Person.age



# print(Person.say(2357475463))              #    一般都不这样使用   ,  类不直接调用 方法   ,应该初始化后调用

tom = Person(23)
# jerry=Person(21)

# print(Person.age)
Person.age=9

print(Person.__dict__)
print(tom.age)
print(tom.__dict__)

# print(Person.age)
# print(jerry.age)
# print(tom.__class__.__dict__)
# print(Person.__dict__)