class Fbo:
    def __init__(self):
        pass
    def __call__(self,m):
        if m ==1 :
            return 1
        elif m==2 :
            return 1
        else:
            pre = 1
            mid = 1
            counter=2
            last =0
            while True:
                last = pre + mid
                pre = mid
                mid = last
                counter+=1
                if counter==m :
                    break
            return last
f = Fbo()
num = f(5)
print(num)