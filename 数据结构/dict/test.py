d= {}
d["w"]=5
# d["default"]="beijingzhan"
print(d)

print(d.get("q"))

print(d)

varofreturn= d.setdefault("default")
varofreturn= d.setdefault("default","welcome")  #无法改变默认值
d["default"]=141
print(varofreturn)
print(d)

