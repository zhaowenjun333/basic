class Tempetarure:
    @classmethod
    def ftoc(cls,f):
        return 5* ( f -32)/9

    @classmethod
    def ctof(cls,c):
        return 9* c /5 +32

    @classmethod
    def ftok(cls,f):
        return 5 * (cls.f - 32) / 9+ 273.15

    @classmethod
    def ktof(cls,k):
        return 9* (k-273.15)/5 +32

print(Tempetarure.ftoc(33))
