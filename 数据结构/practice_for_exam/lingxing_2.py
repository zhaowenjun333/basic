for i in range(1,8):  ##误入歧途
   if i <=4 :
       for j in range(i, i+1):
           print((4-j)*" ",(j*2-1)*"*", end="")
   else:
       for  j  in range(8-i,8-i+1):
             print((4-j)*" ",(j*2-1)*"*", end="")
   print()
