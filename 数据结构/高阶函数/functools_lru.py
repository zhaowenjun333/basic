import functools
import time

@functools.lru_cache()
def add(x, y, z=3):
    time.sleep(z)
    print(x + y)
add(4, 5)
add(4.0, 5)
add(4, 6)
add(4, 6, 3)
add(6, 4)
add(4, y=6)
add(x=4, y=6)
