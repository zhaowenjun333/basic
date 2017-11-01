def trangle_print(n):
    for i in range(1, n+1):
        for j in range(n, 0, -1):
            if i < j:
                print(' '*len(str(j)), end = ' ')
            else:
                print(j, end = ' ')
        print()
trangle_print(8)