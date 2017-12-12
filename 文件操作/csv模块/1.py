import csv
from pathlib import Path
p=Path('test.csv')
print(p)
print(type(p))
parent=p.parent
if not parent:
    parent.mkdir(parent=True)

csv_body="""
id,name,age,comment
1,zs,18,"i a'm 18 years old"
2,ls,20,"this is a 'test' string."
3,ww,23,"中国

国庆"
"""
p.write_text(csv_body)
