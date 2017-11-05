import re,datetime
logline = '''183.60.212.153 - - [19/Feb/2013:10:23:29 +0800] "GET /o2o/media.html?menu=3 HTTP/1.1" 200 16691 "-" "Mozilla/5.0 (compatible; EasouSpider; +http://www.easou.com/search/spider.html)"'''
fields=[]
temp=''
for field in logline.split():
    if field.startswith("[")or field.startswith('"')    :      #短路 或
        temp += field.strip('[]"')
        if field.endswith(']') or field.endswith('"'):
            fields.append(temp)
            temp=""
    elif  field.endswith(']') or field.endswith('"'):
        temp += " " +field[:-1]
        fields.append(temp)
        temp = ""
    else:
        if temp !='':
            temp+=' '+field
        else:
            fields.append(field)
print("____________________________")
print(fields)