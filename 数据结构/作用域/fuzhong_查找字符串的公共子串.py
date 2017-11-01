"""定义一个函数， 功能：
findstr(targetstr, substr)
在targetstr里面查找 substr的 最长的子串
"""



def findsubstr(targetstr, substr):
    maxsrr=''
    i = len(substr)
    while True:
        count = 0
        for j in range(0,i-count):
            ind=targetstr.find(substr[count:count+i])     #下标
            if ind !=-1 :
                maxsrr=substr
                break
            count += 1
    return maxsrr
str1="aaabbaaaa"
str2="abb"
num = findsubstr(str1,str2)
print(num)