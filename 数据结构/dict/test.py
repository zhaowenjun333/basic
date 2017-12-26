d= {}
d["w"]=5
# d["default"]="beijingzhan"
print(d)

print(d.get("q"))

print(d)

varofreturn= d.setdefault("q",7)
print(d.get("q"))
varofreturn= d.setdefault("q","welcome")  #无法改变默认值  D.get(k,d), also set D[k]=d if k not in D "    第一次已经设置过了所以 第二次无法再设置了  ,所以会返回 value

print(d.get("q"))
d["q"]=141

print(d.get("q"))

print(varofreturn)     #
print(d)

