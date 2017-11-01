adict={'a':{'b':1,'c':2}, 'd':{'e':3,'f':{'g':4}}}
def dictflat(dct,lstinner = []):
    for k, v in dct.items():
        if type(v) == dict:
            lstinner.append("{}.".format(k))
            dictflat(v)
        else:
            lstinner.append("{}={}".format(k,v))
    return lstinner
print(dictflat(adict))
