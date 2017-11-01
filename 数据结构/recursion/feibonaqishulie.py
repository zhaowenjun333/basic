def fib(n):
    return 1 if n< 2 else fib(n-1)+fib(n-2)

    # return   n<2  and  1  or fib(n-1)+fib(n-2)
a = fib(10)
print(a )

