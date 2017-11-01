


def cashe(fn):

    def wrapper(x,y):
        dict={}


        fn(x,y)

    return wrapper
@cashe
def add(x,y):
    return x+y



# lst=list(["nhao","dajiahao"])
# print(type(enumerate(lst)))
# print(enumerate(lst))
# for i in enumerate(lst):
#     print(i)