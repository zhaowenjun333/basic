import csv
from pathlib import Path



with open('test.csv',encoding='utf-8',mode='tr') as f :
    reader=csv.reader(f)
    print(next(reader))
    print(next(reader))
    print(next(reader))

row=[4,'tom',22,'tom']

rows=[
    (4,'jerry',23,'tom'),
    (5,'justin',24,'tom')
]

with open("test.csv" ,'a+') as f :
    writer=csv.writer(f)
    writer.writerow(row)
    writer.writerow(rows)