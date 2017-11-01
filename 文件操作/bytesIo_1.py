

from io import  StringIO

sio=StringIO()
print(sio.closed)
sio.write("123\n456magedu\nhelloworld")
sio.seek(0)
print(sio.readline())
print(sio.readline())
print(sio.getvalue())
sio.close()