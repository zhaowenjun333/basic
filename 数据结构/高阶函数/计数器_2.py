def counter(base=1):
    def incc(step):
        nonlocal base
        base =base +step
        return base
    return incc

a =counter(3)
print(a)

print(a(3))
print(a(3))
print(a(3))
print(a(3))