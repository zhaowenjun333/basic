lst=[2,5]
print(list(map(str,lst)))
print(map(str,lst))
for i in map( str, lst):
    print(i)
    print(type(i))