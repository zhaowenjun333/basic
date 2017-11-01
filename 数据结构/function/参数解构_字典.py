def adder(x,y):
    return x+y

d ={'x':4,'y':5}
print(adder(**{'x':4,'y':5}))
print(adder(*d.keys()))
print(adder(*d.values()))

def pr():
    return None
print(type(pr()))