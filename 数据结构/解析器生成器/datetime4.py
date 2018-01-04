import datetime
a = datetime.datetime.now()
print(a)

generators  = (i  for i in range(10))
while True:
    try:
        print(next(generators))
    except Exception as e :
        print(e)
        break
