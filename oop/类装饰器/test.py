#this is magic method
class T:

    def __lt__(self, other):
        pass
    def __sizeof__(self):
        return super().__sizeof__()

    def __getattribute__(self, name: str):
        return super().__getattribute__(name)

    def __ne__(self, o: object) -> bool:
        return super().__ne__(o)

    def __reduce_ex__(self, protocol: int) -> tuple:
        return super().__reduce_ex__(protocol)

    def __repr__(self) -> str:
        return super().__repr__()

    def __format__(self, format_spec: str) -> str:
        return super().__format__(format_spec)

    def __init__(self) -> None:
        super().__init__()

    def __setattr__(self, name: str, value) -> None:
        super().__setattr__(name, value)

    def __reduce__(self) -> tuple:
        return super().__reduce__()

    def __hash__(self) -> int:
        return 3

    def __new__(cls):
        return super().__new__(cls)

    def __str__(self) -> str:
        return super().__str__()

    def __delattr__(self, name: str) -> None:
        super().__delattr__(name)

    def __eq__(self, o: object) -> bool:         # 去重
        return True

print(hash(T()))
print(T().__hash__())
print(T.__mro__)

a =set()
a.add(T())
print(a)
a.add(T())
print(a)