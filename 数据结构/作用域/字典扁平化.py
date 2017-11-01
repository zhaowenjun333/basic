adict={'a':{'b':1,'c':2}, 'd':{'e':3,'f':{'g':4}}}

"""
def dictflat(dct,lst=[],lst1=[]):
    for k,v in dct.items():
        if type(v)==dict:
            lst.append("{}.".format(k))
            dictflat(v)
        else:
            return lst.append("{}={}".format(k,v))
        # lst.append(".{}={}".format(k,v))
        lst1.append(lst)
    return lst1


print(dictflat(adict))


"""
def dictflat(dct):

    while True:

        for k,v in dct:
            if type(v) == dict:
                lst.append("{}.".format(k))
                dct = v
            else:
                 lst.append("{}={}".format(k, v))
                 break

