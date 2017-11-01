'''
def findit(str1,str2):
    count=0
    length=len(str1)
    for sublen in range(length,0,-1):
        for start in range(0,length-sublen+1):
            subst= str1[start:start+sublen]
            count+=1
            if str2.find(subst) >-1:
                return subst

print(findit("aljsrtylgalg","aaaajsrtydjdmdm"))
'''
"""
aljsrtylgalg    0
aljsrtylgal     1
aljsrtylga      2
aljsrtylg        3

def both(substr, s):
    '''
    求2个字符串的最长公共子串
    abcdef
    '''
    length = len(substr)
    s = str(s)
    g = { substr[i:j] for i in range(length) for j in range(length, 0, -1)}

    def compare(g, s):
       return max([ i for i in g if i in s], key=len)

    return compare(g, s)

print(both('f', 'xbc de fghi'))

"""
def findit(str1,str2):
    count=0
    length=len(str1)
    for sublen in range(length,0,-1):
        interval=length-sublen
        for k in range(0,interval+1):
           if  str2.find(str1[k:k+sublen]) !=-1:
               return str1[k:k+sublen]


print(findit("aljsralg","aaaajjjsradmdm"))