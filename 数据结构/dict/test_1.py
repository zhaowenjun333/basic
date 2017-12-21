adict = dict()
adict=dict.fromkeys(range(10))

print(adict)
print(type(adict))

for i in adict:
    print(i)


for i ,j  in adict.items():
    print(i,j)

for i in adict.keys():
    print(i)

for i in adict.values():
    print(i)

