pre=1
mid=1
print(pre,mid,end=' ')
while True:
    last= pre +mid
    if last >100:
        break
    pre = mid
    mid=last
    print(last,end=" ")

