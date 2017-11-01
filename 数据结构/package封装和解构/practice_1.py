str1 ="JAVA_HOME=/usr/bin"
*_,a= str1.partition("=")

print(a)

str1 ="JAVA_HOME=/usr/bin"
x,y= str1.split("=")
print(x,'\n',y)