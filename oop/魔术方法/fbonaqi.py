class Fbo:
    def __init__(self):
        self.list  = [0,1,1]
        self.length=len(self.list)
    def __call__(self,m):
         if m<len(self.list):
             return self.list[:m]
         else:
             self.length = len(self.list)
             for i in range(self.length-1,m):
                 self.list.append(self.list[i-1]+self.list[i])
             return self.list
    def __getitem__(self, item):
        return self.list[item:item+1]
    def __iter__(self):
        return iter(self.list)
f = Fbo()
num = f(10)
print(num)
num = f(20)
# num = f(20)
print(num)
print(num[4])