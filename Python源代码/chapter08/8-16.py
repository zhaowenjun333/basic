#!/usr/bin/env python
# -*- coding: utf-8 -*-

from sqlalchemy import Table, Column, Integer, ForeignKey, String
from sqlalchemy.orm import relationship, backref
from sqlalchemy.ext.declarative import declarative_base

Base = declarative_base()

class Class(Base):
    __tablename__ = 'class'
    class_id = Column(Integer, primary_key=True)
    name= Column(String(50))
    level = Column(Integer)
    address = Column(String(50))

    class_teachers = relationship("ClassTeacher", backref="class")
    students = relationship("Student", backref="class")
	
class Student(Base):
    __tablename__ = 'student'
    student_id = Column(Integer, primary_key=True)
    name= Column(String(50))
    age = Column(Integer)
    gender= Column(String(10))
    address= Column(String(50))
    class_id = Column(Integer, ForeignKey('class.class_id'))

class Teacher(Base):
    __tablename__ = 'teacher'
    teacher_id = Column(Integer, primary_key=True)
    name= Column(String(50))
    gender= Column(String(10))
    telephone= Column(String(50))
    address= Column(String(50))
    class_teachers = relationship("ClassTeacher", backref="teacher")

class ClassTeacher(Base):
    __tablename__ = 'class_teacher'
    teacher_id = Column(Integer, ForeignKey('teacher.teacher_id'), primary_key=True)
    class_id = Column(Integer,ForeignKey('class.class_id'),primary_key=True)


from sqlalchemy import create_engine
from sqlalchemy.orm import scoped_session, sessionmaker

db_connect_string='sqlite://'

engine = create_engine(db_connect_string)
SessionType = scoped_session(sessionmaker(bind=engine,expire_on_commit=False))
def GetSession():
    return SessionType()




Base.metadata.create_all(engine)
session = GetSession()

session.add(Class(name = "三年二班", level = 3, address = "B3F1"))
session.add(Class(name = "三年三班", level = 3, address = "B3F2"))
session.add(Class(name = "一年三班", level = 1, address = "B2F1"))

session.add(Student(name = "王晓帅", age=9, gender='男', address = "浦东新区",class_id=1))
session.add(Student(name = "林文龙", age=9, gender='男', address = "黄浦区",class_id=1))
session.add(Student(name = "李霞", age=10, gender='女', address = "B3F1",class_id=1))
session.add(Student(name = "侯梦", age=9, gender='女', address = "南汇县",class_id=2))

session.add(Teacher(name = "王天来", gender='男', telephone='56340688', address = "浦东新区"))
session.add(Teacher(name = "吴芬", gender='女', telephone='34129983', address = "浦东新区"))

session.add(ClassTeacher(teacher_id = 1, class_id =1))
session.add(ClassTeacher(teacher_id = 1, class_id =2))
session.add(ClassTeacher(teacher_id = 1, class_id =3))
session.add(ClassTeacher(teacher_id = 2, class_id =1))
session.add(ClassTeacher(teacher_id = 2, class_id =2))

class_=session.query(Class).filter(Class.name=="三年二班").first()
for student in class_.students:
    print(("学生姓名：%s, 年龄：%d" %(student.name, student.age)))


print("------------------------------------------")


class_=session.query(Class).filter(Class.name=="三年二班").first()
for class_teacher in class_.class_teachers:
    teacher = class_teacher.teacher			
    print(("老师姓名：%s, 电话：%s"%(teacher.name, teacher.telephone)))


sStudents = session.query(Student).join(Class).filter(Class.level==3).all()
for student in sStudents:
    print((student.name))

