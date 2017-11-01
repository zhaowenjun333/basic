
"""
ConfigParser   对象 改变属性section   option  value   后,  记住落地  ,

"""

from configparser import ConfigParser

cfg=ConfigParser()
cfg.read("mysql.ini",encoding='utf-8')   # """Read and parse a filename or a list of filenames.
print(cfg.sections())    # return a list of
print(cfg.has_section('client'))

print(cfg.items('mysqld'))
for k,v in cfg.items():
    print(k,"==" ,v,type(v))

tmp= cfg.get('mysqld','port')   # 文本类型本来就是 str  类型的
print(type(tmp),tmp)

print(cfg.get('mysqld','a'))    # 没有 option 就会去默认的里面找     默认里面定义的是option 而不是section
# print(cfg.get('mysqld','magedu'))  # 会报错,默认option里面没有
print(cfg.get('mysqld','magedu',fallback="python"))  #  给option 一个临时的 value

tmp= cfg.getint('mysqld','port')    # 获取整型的 value
print(type(tmp),tmp)

if  not cfg.has_section('test'):
    cfg.add_section("test")
    cfg.write(open("mysql.ini", 'w',encoding="utf-8"))

if cfg.has_section('test'):
    print(cfg.has_section('test'))
    cfg.remove_section('test')
    cfg.remove_section('mysql')
    cfg.write(open("mysql.ini",'w',encoding="utf-8"))  #
    if not cfg.has_section('我就是任性啊'):
        cfg.add_section("我就是任性啊")
        cfg.write(open("mysql.ini",'w',encoding="utf-8"))



