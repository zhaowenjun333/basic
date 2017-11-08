def setnameproperty(name):
    def wrapp(cls):
        cls.NAME = name
        return cls
    return wrapp
# Myclass=setnameproperty(name)(MyClass)
@setnameproperty("this is my class name called zhaoyuncreated class ")
class MyClass:
    pass

print(MyClass.__dict__)