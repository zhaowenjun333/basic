# from lklst import LinkedList
class DoubleLinkList:
    class _Node:
        def __init__(self, value, link=None,head=None):
            self.data = value
            self.link = link
            self.head=head
            # self.tail=None
        def __str__(self):
            return str(self.data)
    def __str__(self):       #模拟链表打印
        rtstr="⇥"
        if self.head != None:
            p = self.head  # 找到第一个节点
            while True :
                if p.link != None:
                    rtstr+=str(p.data)+"<->"
                else:
                    rtstr += str(p.data)
                    break
                p = p.link
        else:
             pass
        return rtstr + "⇤"

    def __init__(self,args=[]):
        if len(args)!=0:
            self.head=DoubleLinkList._Node(args[0])
            thisnd = self.head
            for i in args[1:len(args)]:
                nextnd = DoubleLinkList._Node(i)
                thisnd.link = nextnd
                nextnd.head = thisnd
                thisnd=nextnd
        else:
            self.head=None

    def length(self):
        thisnd = self.head
        length = 0
        while thisnd != None:
            length += 1
            thisnd = thisnd.link
        return length

    def getitem(self,index):
        if self.length()<=index:
            return "outoffindex"
        else:
            j = 0
            p = self.head    # 找到第一个节点
            while p.link != 0 and j < index:  #短路与都为真才会进入
                p = p.link
                j += 1
            if j == index:
                return p.data
    def __getitem__(self,index):
        if self.length()<=index:
            return "outoffindex"
        else:
            j = 0
            p = self.head    # 找到第一个节点
            while p.link != 0 and j < index:  #短路与都为真才会进入
                p = p.link
                j += 1
            if j == index:
                return p.data

    def append(self,item):
        thisnd = DoubleLinkList._Node(item)
        if self.head ==None:
            self.head = thisnd
        else:
            p = self.head
            while p.link!=None:
                p = p.link
            p.link = thisnd
            thisnd.head=p
    def pop(self):
        if self.head == None:
           return "doublilinkedlist is empty"
        elif self.length()==1:
             deltemp=self.head
             self.head =None
             return  deltemp
        else:
            p = self.head
            tempnd=p
            while True:
                if p.link ==None:
                    deltemp =p
                    p.head=None
                    tempnd.link=None
                    del p
                    return deltemp
                else:
                    tempnd=p
                    p = p.link
    def __setitem__(self, key, value):
        thisnd = DoubleLinkList._Node(value)
        if self.length() < key:
            if self.head == None:
                self.head = thisnd
            else:
                return "outoffindex"  # 追加也可以
        elif self.length() == key:
            self.append(thisnd)
        else:
            j = 0
            p = self.head
            while p.link != None and j < key:
                p = p.link
                j += 1
            p.data= value

    def insert(self,item,pos):
        thisnd = DoubleLinkList._Node(item)
        if self.length() < pos:
            if self.head == None:
                self.head = thisnd
            else:
                return "outoffindex"#追加也可以
        elif self.length()==pos:
            self.append(thisnd)
        else:
            j=0
            p = self.head
            if 0 == pos:
                # orientlinknd=p.head
                self.head = thisnd
                thisnd.link = p
                thisnd.head=None
                # thisnd.head=orientlinknd

            else:
                while p.link != None and j<pos:
                    p = p.link
                    j += 1
                orientlinknd = p.head
                p.head = thisnd
                thisnd.link = p
                thisnd.head = orientlinknd
                if pos != 0:
                    orientlinknd.link = thisnd
    def  remove(self,pos):
        if self.length() <= pos:
            return "noelement"
        else:
            j = 0
            p = self.head
            orientlinknd=p
            while  j < pos:
                orientlinknd=p
                p = p.link
                j += 1
            if p.link==None:
                orientlinknd.link=None
            else:
                suffixnd=p.link
                orientlinknd.link=suffixnd
                suffixnd.head=orientlinknd
                return p

    def iternodes(self):                    # 封装一个
        if self.head != None:
            p = self.head  # 找到第一个节点
            while True :
                yield p.data
                if p.link==None:
                    break
                p = p.link
        else:
            print("linklist is empty")
    def __iter__(self):                   # 封装一个
        if self.head != None:
            p = self.head  # 找到第一个节点
            while True :
                yield p.data
                if p.link==None:
                    break
                p = p.link
        else:
              yield "linklist is empty"

dll=DoubleLinkList( [456,23,253])
# print(dll.length())
# print(dll.getitem(0))
# print(dll.getitem(1))
# print(dll.getitem(3))
# dll.append("dkfkh")
# for i in dll.iternodes():
#     print(i)
#
for i in dll:
    print(i)
#

print(dll)
dll.insert(1475673,1)
print(dll)
# print('++++++++++++++++++++++++++++')
# print(dll.pop())
# print(dll)
# print(dll.pop())
# print(dll)
# print(dll.pop())
# print(dll)
# print(dll.pop())
# print(dll)
# print(dll.pop())
# print(dll)
# print(dll)
# print('++++++++++++++++++++++++++++')
# print(dll.pop())
# print(dll)
print(dll.pop())
print(dll)
print(dll.remove(2))
dll.remove(6)
print(dll)