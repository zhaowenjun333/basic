# lst= []
def reversenum(n):
    if n < 10 and n > 0 :
        b=[]
        print(n)
        return b.append(n)
    else:
        a=[]
        print("n",n)
        a.append(n%10)
        print("lst",a)
        return a.extend(reversenum(n//10))
lstre=reversenum(431) #   4*3*2*1
print(lstre)

# ls=[]
# ls.append()