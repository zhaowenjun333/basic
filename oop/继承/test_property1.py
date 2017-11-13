class Animal:
    pass

class Cat(Animal):
    pass

object()
print(Cat().__class__.__bases__)