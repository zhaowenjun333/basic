import  time,datetime
class  Mathcc:
    def __init__(self,fn):
        self._fn=fn
        print("init")

    def __enter__(self):
        self.start=datetime.datetime.now()
        return self

    def __exit__(self, exc_type, exc_val, exc_tb):
          print((datetime.datetime.now()-self.start).total_seconds())

    def __call__(self, *args,**kwargs):                      #可调用
        return self._fn(*args,**kwargs)

@Mathcc
def add(x ,y):
    time.sleep(2)
    return  x+y


print(add(5,6))