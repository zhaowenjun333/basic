a = [(i+1)**2  for i in range(10) ]
print(a )
lst=[1,4,9,16,2,5,10,25]
temp=0
# b =[ for  j in lst  ]

# [print("{}*{}={}".format(i,j,j*i),end="\t") for i in range(1,10)  for j in  range(1,i+1)  ]
print('\n'.join([''.join(['{}*{}={:<3}'.format(x,y,y*x) for x in range(1,y+1)]) for y in range(1,10)]))