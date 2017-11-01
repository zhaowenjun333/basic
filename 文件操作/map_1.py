a =map(lambda x: (x,x+1) ,range(5))  #sorted()     filter()

# c=a
# print(type(a))
# print(iter(a))
# for i in a :
#     print(i)
b=dict(a)
print(b)

print(b[0])

for k,v in b.items():
    print("k",k,end=" ")
    print(v)
