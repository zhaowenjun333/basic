"""("""
"""
             n               桃子总数
day1            1              day1 /2   -1 = eatpeach(2) ->   day1 = (eatpeach(2)+1 )2
day2                                          .                       eatpeach(3)+1 )2                   
day3                                          .                       eatpeach(4)+1 )2
.
.
day10
"""
def  eatpeach(n):
    return  1 if n <=1 else  (eatpeach(n-1)+1)*2
result =eatpeach(10)   #
print(result)