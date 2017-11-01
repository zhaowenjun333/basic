from collections import namedtuple
Student = namedtuple('Student','name,age') # 可以是列表    [ 'name','age']
tom = Student('tom', 20)
jerry = Student('jerry', 18)
print(tom.name)
