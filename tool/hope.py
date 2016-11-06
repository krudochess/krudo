import sys,os

dst = sys.argv[1]

con = "";

def cel(r,c):
	if 0 <= r and r < 8 and 0 <= c and c < 8:
		return chr(ord("a")+c)+chr(ord("1")+r)
	else:
		return "xx"

for r in range(0,8):
	for c in range(0,8):
		con += "/*"+cel(r,c)+":*/{"
		row = [ 
			cel(r-2,c-1),
			cel(r-2,c+1),
			cel(r+2,c-1),
			cel(r+2,c+1),		
			cel(r-1,c+2),
			cel(r+1,c+2),
			cel(r-1,c-2),
			cel(r+1,c-2)
		]
		row.sort()
		con += ",".join(row) + "}," + "\n"

open(dst,"w").write(con)






