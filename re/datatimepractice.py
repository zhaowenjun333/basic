from datetime import datetime

# print(datetime)
print(type(datetime))
print(datetime.now())
# print(datetime.now().timestamp())
print(type(datetime.now()))
# print(type(datetime.now().timestamp()))
# timeobj=datetime.strptime("19/Feb/2013:10:23:29 +0800","%d/%b/%Y:%H:%M:%S %z")     #字符串 解析   为时间对象
timeobj=datetime.strptime("19/Feb/2013:10:23:29 ","%d/%b/%Y:%H:%M:%S ")     #字符串 解析   为时间对象
print(timeobj)
print(type(timeobj))
strtime=datetime.now().strftime("%Y-%m-%d %H:%M:%S")
print(strtime)
print(type(strtime))

