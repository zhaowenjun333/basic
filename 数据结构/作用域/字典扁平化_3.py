adict={'a':{'b':1,'c':2}, 'd':{'e':3,'f':{'g':4}}}
# target={}
def dictflat(dct,dest={},prefix=""):
    for k, v in dct.items():
        if type(v) == dict:  #isinstance(v,(list,tuple,set,dict))
            dictflat(v,prefix=prefix+k+'.')
        else:
            dest[prefix+k]=v
    return dest
print(dictflat(adict))