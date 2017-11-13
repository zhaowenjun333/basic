"""利用装饰器给  类增加方法和属性

强烈推荐这样的方法实现

好处管理起来非常便利,插件一样 一行管理 ,开关的形式
"""


def property_setnameproperty(name):
    def wrapp(cls):
        cls.NAME = name
        return cls
    return wrapp

def methond_printable(cls):
    cls.print=lambda self:self.content
    return cls
# Myclass=setnameproperty(name)(MyClass)
@property_setnameproperty("this is my class name called zhaoyuncreated class ")
@methond_printable
class MyClass:
    pass

print(MyClass.__dict__)