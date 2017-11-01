astring = input("input  a number ")

lst = list(astring)
print(lst)
print(len(lst))
while True :
    last= lst.pop()
    print(last)
    if len(lst) == 1:
        break

