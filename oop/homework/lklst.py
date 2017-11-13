
class LinkedList:
    class _Node:
        def __init__(self, value, link=None):
            self.data = value
            self.link = link
            self.head=None
    def __str__(self):       #模拟链表打印
        rtstr="["
        if self.head != None:
            p = self.head  # 找到第一个节点
            while True :
                if p.link != None:
                    rtstr+=str(p.data)+"->"
                else: rtstr += str(p.data); break
                p = p.link
        else:
             pass
        return rtstr + "]"

    def __init__(self,args=[]):
        if len(args)!=0:
            self.head=LinkedList._Node(args[0])
            thisnd = self.head
            if len(args)!=1:
                for i in args[1:]:                 #不会担心越界
                    node = LinkedList._Node(i)
                    thisnd.link = node
                    thisnd = node
        else:
            self.head=None

    def getitem(self,index):
        j = 0
        p = self.head    # 找到第一个节点
        while p.link != 0 and j < index:  #短路与    都为真才会进入
            p = p.link
            j += 1
        if j == index:
            return p.data

    def append(self,item):
        thisnd = LinkedList._Node(item)
        if self.head ==None:
            self.head = thisnd
        else:
            p = self.head
            while p.link!=None:
                p = p.link
            p.link = thisnd

    def insert(self):
        pass

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

print("测试_________________________________")
ll=LinkedList([1,"what",list(range(123,3234,365)),LinkedList([1,"what",list(range(67,3234,365)),])])
print(ll)
# print(ll.getitem(1))
for i in ll.iternodes():
    print(i)

ll.append(3434)
print(33333333333333333333333333333333333,ll.getitem(0))
for i in ll.iternodes():
    print(i)





