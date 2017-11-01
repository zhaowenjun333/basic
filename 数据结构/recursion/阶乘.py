def  factorial(n):
    return  1 if n <=1 else n*factorial(n-1)

result =factorial(4)   #   4*3*2*1
print(result)