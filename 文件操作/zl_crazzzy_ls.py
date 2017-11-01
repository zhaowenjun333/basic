import argparse
from pathlib import Path
parser = argparse.ArgumentParser(add_help=False)

parser.add_argument("-a",dest='alal',action="store_true", default=True, )
parser.add_argument("-l",dest='ll',action='store_true'  )
parser.add_argument("--help",dest='help',action='store_true'  )
parser.add_argument("-h",dest='human',action='store_true'  )
parser.add_argument("file" ,action='store' , default="c:/users",nargs="*" )
# parser.add_help("-l", help="list per")
args = parser.parse_args()
# if args.ls=='ls':

if args.human:
    print("changge")

if args.help:
    parser.print_help()

else:
    if args.ll:

        if args.file:
            p = Path(args.file)
        else:
            p = Path()
        for i in p.iterdir():
            p.stat()
            print("{:^20} {:^20} {:^20}{:^20} {:^20}{:^20} {:^20}".format("a", i.name, "b", "c", "d","e","f"))
    else:
        if args.alal:  # ->bool

            if args.file:
                p = Path(args.file)
                # print(args.file)
            else:
                p = Path()
            countline=0
            for i in p.iterdir():
                countline+=1
                print("{:^4}".format(i.name),end="   ")
                if countline%15==0:
                    print()



