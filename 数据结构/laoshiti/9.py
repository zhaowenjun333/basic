logstr="""116.226.208.136 - - [28/Apr/2015:09:01:38 +0800] "GET /js/check.js HTTP/1.1" 304 -
59.53.22.67 - - [28/Apr/2015:09:01:38 +0800] "GET /jquery/jquery.datepick.css HTTP/1.1" 304 -
117.93.56.165 - - [28/Apr/2015:09:01:38 +0800] "GET /jquery/jquery-1.4.2.js HTTP/1.1" 304 -
106.39.189.200 - - [28/Apr/2015:09:01:38 +0800] "GET /jquery/jquery.datepick.js HTTP/1.1" 304 -
219.146.71.17 - - [28/Apr/2015:09:01:38 +0800] "GET /jquery/jquery.datepick-zh-CN.js HTTP/1.1" 304 -
111.11.83.162 - - [28/Apr/2015:09:01:38 +0800] "GET /images/shim.gif HTTP/1.1" 304 -
117.93.56.165 - - [28/Apr/2015:09:01:38 +0800] "GET /images/button_ok.gif HTTP/1.1" 304 -
111.206.221.200 - - [28/Apr/2015:09:01:38 +0800] "GET /images/button_cancel.gif HTTP/1.1" 304 -
112.80.144.85 - - [28/Apr/2015:09:01:46 +0800] "GET /user/list.jsp HTTP/1.1" 200 7644
117.148.200.56 - - [28/Apr/2015:09:01:46 +0800] "GET /images/i_edit.gif HTTP/1.1" 304 -
183.12.49.80 - - [28/Apr/2015:09:01:46 +0800] "GET /images/i_del.gif HTTP/1.1" 304 -
175.19.57.147 - - [28/Apr/2015:09:01:46 +0800] "GET /images/button_view.gif HTTP/1.1" 304 -
117.136.63.218 - - [28/Apr/2015:09:05:46 +0800] "GET /user/list.jsp HTTP/1.1" 200 7644
157.55.39.102 - - [28/Apr/2015:09:05:56 +0800] "GET /login.jsp HTTP/1.1" 200 2607
111.206.221.68 - - [28/Apr/2015:09:05:58 +0800] "POST /user_login.action HTTP/1.1" 200 2809
117.93.56.165 - - [28/Apr/2015:09:06:12 +0800] "POST /user_login.action HTTP/1.1" 302 -
223.98.218.205 - - [28/Apr/2015:09:06:12 +0800] "GET /login/home.jsp HTTP/1.1" 200 743
117.136.97.78 - - [28/Apr/2015:09:06:12 +0800] "GET /login/welcome.jsp HTTP/1.1" 200 1142
111.206.221.68 - - [28/Apr/2015:09:06:12 +0800] "GET /login.jsp HTTP/1.1" 200 803
117.93.56.165 - - [28/Apr/2015:09:06:12 +0800] "GET /login/top.jsp HTTP/1.1" 200 2052
111.206.221.68 - - [28/Apr/2015:09:06:13 +0800] "GET /login.jsp HTTP/1.1" 200 1113
"""
loglist = logstr.splitlines()
countjsp = 0
for  i in  loglist:
    if i.count(".jsp") !=0 and i.count(" 200 ") !=0 :
        print(i)
        countjsp+= int (i.split(" 200 ")[1])
print("total of jsp_200  is :",countjsp)


effectloglist = []
for i in loglist:
    if i[-1]!="-":
        effectloglist.append(i)
print(effectloglist)

adict ={}
countjc = 0
countjjpn = 0
countajd = 0
for  i in  effectloglist:
    if i.count(".js")!=0  or i.count(".css")!=0:
         countjc+=int (i.split(" 200 ")[1])
    if i.count(".jpg") != 0 or i.count(".jpeg") != 0 or i.count(".gif") != 0 or i.count(".png") != 0:
        countjjpn+= int (i.split(" 200 ")[1])
    if i.count(".action") != 0 or i.count(".jsp") != 0 or i.count(".do") != 0:
        countajd += int (i.split(" 200 ")[1])
adict["countjc"]=countjc
adict["countjjpn"]=countjjpn
adict["countajd"]=countajd
print(adict)
