class Person:
    @classmethod
    def class_method(cls):
        cls.HEIGHT=170
    @staticmethod
    def static_method():       #静态方法没有任何参数
        print(Person.HEIGHT)

Person.class_method()
print(Person.__dict__)
print(Person.HEIGHT)           # 类变量  已经赋值了,可以直接调用
Person.static_method()
Person().class_method()