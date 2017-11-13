
class Word:pass

#一个参数的装饰器很简单 只需要一层

def printable(cls):
    def print():
        pass   #加强的  打印
    cls.print=lambda self:print(self.content)
    return cls




@printable       # PrintableWord=printable(PrintableWord)
class PrintableWord(Word):
    pass