class Point:
    def __init__(self,x,y):
        self.x=x
        self.y=y

    def __eq__(self, other):
        return self.x==other.x  and self.y == other.y

    def __add__(self, other):
        self.y+=self.y
        self.x+=self.x
        return self
    def __str__(self):
        return str((self.x,self.y))
flag = Point(1,2) ==Point(1,3)
print(flag)
print(Point(1,2)+Point(1,2))
