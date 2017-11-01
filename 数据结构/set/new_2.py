seta = set([1,19,2,18,4,48,29,4,29,2,3])
print(seta)
seta.add(386396)
seta.update("zhaoyun","a","b","c","d","e","f","g")
print("zhaoyun" in seta)

print(seta)

for  i  in range(20):
    b = seta.pop()
    print(b)
