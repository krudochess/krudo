import sys,os

src = sys.argv[1]
dst = sys.argv[2]

con = open(src,'r').read()

#con = 

#print con

for name in os.environ:
	con = con.replace('%'+name+'%',os.environ[name].replace('\\','/'))

open(dst,"w").write(con)



