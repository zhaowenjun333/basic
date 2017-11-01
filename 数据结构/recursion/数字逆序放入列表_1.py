data = str(1234)
def revert(x):
    if x==-1:
        return ""
    else:
        return data[x]+revert(x-1)
print(revert(len(data)-1))
print( type(revert(len(data)-1)))
print(data)