import  time,datetime
class  Mathcc:
    def __init__(self):
        print("init")

    def __enter__(self):
        self.start=datetime.datetime.now()

    def __exit__(self, exc_type, exc_val, exc_tb):
          print((datetime.datetime.now()-self.start).total_seconds())

def add(x ,y):
    time.sleep(2)
    return  x+y

with Mathcc() as f :
    print(add(3,4))