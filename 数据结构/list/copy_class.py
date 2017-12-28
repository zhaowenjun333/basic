"""
Number（数字）
    int（整型）
    float（浮点型）
    complex（复数）
    bool（布尔）
String（字符串）
List（列表）
Tuple（元组）
Sets（集合）
Dictionary（字典）
"""
class Person:
    def __init__(self,name):
        self.name = name
    def __repr__(self):
        return self.name
lst0 = [1,Person("小明"), 5]
lst1 = lst0.copy()
print(lst0 ==lst1,lst1,lst0,id(lst1),id(lst0))
lst0[1].name="小云"
print(lst0 ==lst1,lst1,lst0,id(lst1),id(lst0))
