# coding: utf-8
from sqlalchemy import Boolean, Column, DateTime, ForeignKey, Integer, String
from sqlalchemy.orm import relationship,backref
from sqlalchemy.ext.declarative import declarative_base


Base = declarative_base()
metadata = Base.metadata


class Account(Base):
    __tablename__ = 'account'

    # user roles
    USER = 100
    ADMIN = 300
    
    id = Column(Integer, primary_key=True)
    language_id = Column(ForeignKey('language.id'), nullable=False)
    user_name = Column(String(20))
    password = Column(String(200))
    email = Column(String(255))
    mobile_phone = Column(String(50))
    version = Column(Integer)
    role = Column(Integer, default=USER)

    language = relationship('Language')

class Apartment(Base):
    __tablename__ = 'apartment'

    id = Column(Integer, primary_key=True, index=True)
    account_id = Column(ForeignKey('account.id'), nullable=False)
    name = Column(String(50))
    arm_state = Column(Integer)
#    scene_id = Column(ForeignKey(u'scene.id'))
    scene_id = Column(Integer)
    dt_arm = Column(DateTime)
    version = Column(Integer)

    account = relationship('Account',backref=backref('apartments', order_by=id))
#    scene = relationship(u'Scene', primaryjoin='Apartment.scene_id == Scene.id')

class ApartmentDevice(Base):
    __tablename__ = 'apartment_device'

    id = Column(Integer, primary_key=True, index=True)
    device_id = Column(ForeignKey('device.id'))
    apartment_id = Column(ForeignKey('apartment.id'))
    relayer_id = Column(Integer)
    name = Column(String(50))
#     flag_notification = Column(String(32))

    device = relationship('Device',backref="apartment_devices")
    apartment = relationship('Apartment',backref="apartment_devices",lazy='joined')
    
    

class Apartment_Relayer(Base):
    __tablename__ = 'apartment_relayer'

    apartment_id = Column(Integer,ForeignKey('apartment.id'), primary_key=True)
    relayer_id = Column(Integer,ForeignKey('relayer.id'), primary_key=True)
    name = Column(String(50))

    apartment = relationship('Apartment',backref=backref('apartment_relayers', order_by=relayer_id))
    relayer = relationship('Relayer',backref=backref('apartment_relayers', order_by=apartment_id))


class Client(Base):
    __tablename__ = 'client'

    id = Column(Integer, primary_key=True, index=True)
    account_id = Column(ForeignKey('account.id'), nullable=False)
    device_token = Column(String(100))
    enable_alarm = Column(Boolean)
    os = Column(String(50))
    dt_auth=Column(DateTime)
    dt_active=Column(DateTime)
    server_id=Column(Integer)
    terminal_code = Column(String(50))

    account = relationship('Account',backref="clients")


class Contactor(Base):
    __tablename__ = 'contactor'

    id = Column(Integer, primary_key=True, index=True)
    apartment_id = Column(ForeignKey('apartment.id'), nullable=False)
    name = Column(String(50))
    mobile_phone = Column(String(50))
    email_addr = Column(String(200))

    apartment = relationship('Apartment',backref="contactors")


class Device(Base):
    __tablename__ = 'device'

    id = Column(Integer, primary_key=True, index=True)
    device_model_id = Column(ForeignKey('device_model.id'), nullable=False)

    uni_code = Column(String(50))

    device_model = relationship('DeviceModel',backref="devices")


class DeviceType(Base):
    __tablename__ = 'device_type'

    id = Column(Integer, primary_key=True)
    name = Column(String(50))

    def __init__(self, id = None, name = None):
        self.id = id
        self.name = name


class DeviceModel(Base):
    __tablename__ = 'device_model'

    id = Column(Integer, primary_key=True, index=True)
    device_type_id = Column(ForeignKey('device_type.id'))
    name = Column(String(50))

    device_type = relationship('DeviceType',backref="device_models")

    def __init__(self, id = None, device_type_id = None, name = None):
        self.id = id
        self.device_type_id = device_type_id
        self.name = name


class Language(Base):
    __tablename__ = 'language'

    id = Column(Integer, primary_key=True, index=True)
    language = Column(String(50))
    
    def __init__(self, id = None, language = None):
        self.id = id
        self.language = language


class Param(Base):
    __tablename__ = 'param'

    param_name = Column(String(500), primary_key=True)
    param_value = Column(String(5000))


class Server(Base):
    __tablename__ = 'server'

    id = Column(Integer, primary_key=True)
    type = Column(Integer)
    address =Column(String(50))
    extern_address=Column(String(50))
    status =Column(String(50))
    dt_active=Column(DateTime)

    import datetime
    def __init__(self, id = None, type = 1, address = '127.0.0.1', extern_address = '127.0.0.1', status = '', dt_active = datetime.datetime.now()):
        self.id = id
        self.type = type
        self.address = address
        self.extern_address = extern_address
        self.status = status
        self.dt_active = dt_active

class Relayer(Base):
    __tablename__ = 'relayer'

    id = Column(Integer, primary_key=True, index=True)
    uni_code = Column(String(50))
    dt_auth=Column(DateTime)
    dt_active=Column(DateTime)
    server_id=Column(Integer)
    
    
    
