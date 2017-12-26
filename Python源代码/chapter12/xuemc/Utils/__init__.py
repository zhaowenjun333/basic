
import sys,os
import imp
imp.reload(sys)
sys.setdefaultencoding('utf8')

from . import Util
if Util.isWindows():
    activate_this = os.path.join(os.path.dirname(os.path.abspath(__file__)),os.path.pardir,'venv/Scripts/activate_this.py')
    exec(compile(open(activate_this).read(), activate_this, 'exec'), dict(__file__=activate_this))
else:
    #linux
    activate_this = os.path.join(os.path.dirname(os.path.abspath(__file__)),os.path.pardir,'venv/bin/activate_this.py')
    exec(compile(open(activate_this).read(), activate_this, 'exec'), dict(__file__=activate_this))
