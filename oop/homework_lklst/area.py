class Shape:
    def __init__(self,length=1,height=2):
        self.__length=length
        self.__height=height
        pass

    def getArea(self):
        return self.__height*self.__length

class Triangle(Shape):
    def __init__(self,height,buttom):
        super().__init__()
        self.__height=height
        self.__buttom=buttom
    def getArea(self):
        return self.__height*self.__buttom

class Rectangle(Shape):
     pass

class Circle(Shape):

    def __init__(self,r):
        self.r=r
    def getArea(self):
        import math
        return math.pi * self.r**2
    def __repr__(self):
        return "{}".format(self.r)
    def obj2json(self):
        return {
            "r":self.r
        }
print("=+++测试++++=")
tragl=Triangle(2,8)
print(tragl.getArea())

rct=Rectangle(4,9)
print(rct.getArea())

ccl=Circle(4)
print(ccl.getArea())
import pickle

# 对象序列化
#内存中
import json
strccl =json.dumps(ccl.obj2json())
print(strccl)
strccl =pickle.dumps(ccl)
print(pickle.loads(strccl))

#到文件
with open("serializationfile.txt",mode="wb") as f :
    pickle.dump(ccl,f)

with open("serializationfile.txt",mode="rb") as f :
    print(pickle.load(f))