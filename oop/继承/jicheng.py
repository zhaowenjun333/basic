
class Word:pass

#一个参数的装饰器很简单 只需要一层

def printable(cls):
    # def print():
    #     pass   #加强的  打印
    cls.print=lambda self,x :print(x)
    return cls  #不返回无法调用了printable(PrintableWord)()  ->  cls.print()



@printable       # PrintableWord=printable(PrintableWord)
class PrintableWord(Word):
    pass

p = PrintableWord()
p.print(32)