import glob
from user_agents import parse
for i in glob.glob('*.log') :
    print (i)



str="Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0"
result =parse(str)

print(result.browser.family)
print(result.browser[2])
print(result )

# ua.browser.family, ua.browser.version_string