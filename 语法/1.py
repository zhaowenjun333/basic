count = 0
for i in range(-3, 4):  # 变量i控制行数
    count += 1
prepace = i > 0 and i or -i
if count <= 3:
    print(" " * prepace + "*" * count)
elif count == 4:
    print("" * prepace + "*" * (7 - prepace * 2))
else:
    print(" " * prepace + " " * (3 - i) + "*" * (4 - prepace))
