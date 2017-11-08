class Person:
    def normal_method():         # 解释器不建议这么写 ,类调用可以执行   ,    实例会报错, 实例只可以调用self的方法
        return "normal"


print(Person.normal_method())
print(Person().normal_method())       #  和属性一样,当 实例没有这个方法的时候 就会调用类方法

print(Person.__dict__)