
class LinkedList:
    class _Node:
        def __init__(self, value, link=None):
            self.data = value
            self.link = link
            self.head=None
    def __str__(self):       #模拟链表打印
        rtstr="😄"
        if self.head != None:
            p = self.head  # 找到第一个节点
            while True :
                if p.link != None:
                    rtstr+=str(p.data)+"->"
                else: rtstr += str(p.data); break
                p = p.link
        else:
            pass
        return rtstr + "😄"

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

    def __getitem__(self,index):
        j = 0
        p = self.head    # 找到第一个节点
        while p.link != 0 and j < index:  #短路与都为真才会进入
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

    def insert(self,key,value):
        j = 0
        p = self.head
        thisnd = LinkedList._Node(value)
        while p.link != 0 and j < key:  # 短路与都为真才会进入
            p = p.link
            j += 1
        orientlinknd = p.link
        thisnd.link = orientlinknd
        p.link = thisnd
    def __setitem__(self, key, value):  #暂时不考虑越界问题
        j=0
        p= self.head
        thisnd = LinkedList._Node(value)
        while p.link != 0 and j < key:  # 短路与都为真才会进入
            p = p.link
            j += 1
        p.data=value
        # orientlinknd = p.link
        # thisnd.link =  orientlinknd
        # p.link = thisnd

    def __iter__(self):# 封装一个
        if self.head != None:
            p = self.head  # 找到第一个节点
            while True :
                yield p.data
                if p.link==None:
                    break
                p = p.link
        else:
            yield "linklist is empty"

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
# ll=LinkedList( )
print(ll)
for i in ll :
    print(i)
print(ll[1])
ll[1]="33333333333333"
print(ll[1])
for i in ll :
    print(i)
# ll.append(3434)
# print(33333333333333333333333333333333333,ll.getitem(0))
# for i in ll.iternodes():
#     print(i)





