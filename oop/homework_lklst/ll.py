class Point_q:
    def  __init__(self,x,y):
        self.x=x
        self.y=y
    def __eq__(self, other):
        return self.x==other.x and self.y==other.y

# print(  Point_q(3,5)==Point_q(3,4))
print(Point_q(3,4) is Point_q(3,4))

print(id(Point_q(3,4)))
print(id(Point_q(6,1)))
print(id(Point_q(3,4)))


a=Point_q(2,3)
b=Point_q(2,3)
print(a is not  b)