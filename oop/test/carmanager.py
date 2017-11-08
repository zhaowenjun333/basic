class Car:
    def __init__(self,mark,color,price,speed):
        self.mark= mark
        self.color=color
        self.price=price
        self.speed=speed
    # def __repr__(self):
    #     return "{}{}{}{}".format()
    def showcarinfo(self):
       print(self.__dict__)

    def addcarengine(self,info):
        self.engine=info


baoma=Car("BMW","black","200$","400KM/S")
bz=Car("Benz","red","100$","500KM/S")
ls=Car("LEXUS","white","70$","360KM/S")
print("品牌 颜色  价位 最高速度")
baoma.showcarinfo()
bz.showcarinfo()
ls.showcarinfo()

ls.addcarengine("vvl")
ls.showcarinfo()
