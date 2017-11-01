from collections import  namedtuple
Student =namedtuple("Student1","name,age")
print(type(Student))
s1 =Student("xiaoming","12")
print(s1)
