class Field:  # int varchar date enum float
    def __init__(self, name, fieldname=None, pk=False, unique=False, default=None, nullable=True, index=False):
        self.name = name
        if fieldname is None:
            self.fieldname = name
        else:
            self.fieldname = fieldname
        self.pk = pk
        self.unique = unique
        self.default = default
        self.nullable = nullable
        self.index = index

    def validate(self,value):
        raise NotImplementedError()
    
    def __get__(self, instance, owner):
        if instance is None:
            return self
        return instance.__dict__[self.name]

    def __set__(self, instance, value):
        self.validate(value)
        instance.__dict__[self.name] =value
    

class IntField(Field):
    def __init__(self, name, field_name=None, pk=False, unique=False, default=None, nullable=True, index=False, auto_increment=False):
        self.auto_increment = auto_increment
        super().__init__(name, field_name, pk, unique, default, nullable, index)

    def validate(self,value): # auto_increment
        if value is None:
            if self.pk:
                raise TypeError()
            if not self.nullable:
                raise  TypeError()
        else:
            if not isinstance(value,int):
                raise TypeError

#全局的session  ,ppt是县城内的
class Session:
    def __init__(self,conn):
        self.conn= conn

    def execute(self,sql,*args):
        try:
            with self.conn as cursor:
                with cursor:
                    cursor.execute(sql,(self.name,self.age))
                    self.conn.commit
        except:
            self.conn.rollback()

    def __enter__(self):
        return self.conn.cursor()

    def __exit__(self, exc_type, exc_val, exc_tb):
        if exc_type:
            self.conn.rollback()
        else:
            self.conn.commit()


class StringField(Field):
    def __init__(self,length=32, name=None, field_name=None, pk=False, unique=False, default=None, nullable=True, index=False):
        self.length = length

        super().__init__(name, field_name, pk, unique, default, nullable, index)

    def validate(self,value):
        if value is None:
            if self.pk:
                raise TypeError()
            if not self.nullable:
                raise  TypeError()
        else:
            if not isinstance(value,str):
                raise TypeError
            if len(value) > self.length:
                raise ValueError()


class Student:
    id = IntField("id")
    name = StringField(64,'name')

    def __init__(self, id, name):
        self.id = id
        self.name = name

    def save(self,session):
        sql = "insert into student(name,age) value (%s,%s)"
        with session as cursor:
            with cursor:
                cursor.execute(sql,self.name, self.age)




s = Student(3, "wewetwt")#中文没有问题把
print(s.id)
print(s.name)