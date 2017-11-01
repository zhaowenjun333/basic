adict={'a':{'b':1,'c':2}, 'd':{'e':3,'f':{'g':4}}}
target={}
def dictflat(dct,prefix=""):
    for k, v in dct.items():
        if type(v) == dict:
            dictflat(v,prefix=prefix+k+'.')
        else:
            target[prefix+k]=v
dictflat(adict)
print(target)
