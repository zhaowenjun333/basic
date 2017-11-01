"""定义一个函数， 功能：
findstr(targetstr, substr)
在targetstr里面查找 substr出现的次数
"""




def findsubstr(targetstr, substr):
    count =0
    i = len(substr)
    while True:
        ind=targetstr.find(substr)     #xiabiao
        if ind ==-1 :
            break
        count+=1
        targetstr=targetstr[ind+i:]
    return  count
str1="aaabbaaabbaa"
str2="bba"
num = findsubstr(str1,str2)
print(num)