

from io import  BytesIO

sio=BytesIO()
print(sio.closed)
sio.write(b"123\n456magedu\nhelloworld")
sio.seek(0)
print(sio.readline())
print(sio.readline())
print(sio.getvalue())
sio.close()