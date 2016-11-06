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
		con += cel(r-1,c+0) + ","
		con += cel(r+1,c+0) + ","
		con += cel(r+0,c+1) + ","
		con += cel(r+0,c-1) + ","
		con += cel(r-1,c-1) + ","
		con += cel(r-1,c+1) + ","
		con += cel(r+1,c+1) + ","
		con += cel(r+1,c-1) + "}," + "\n"

con += "-----\n";

for r in range(0,8):
	for c in range(0,8):
		con += "/*"+cel(r,c)+":*/{"
		con += cel(r-2,c-1) + ","
		con += cel(r-2,c+1) + ","
		con += cel(r+2,c-1) + ","
		con += cel(r+2,c+1) + ","		
		con += cel(r-1,c+2) + ","
		con += cel(r+1,c+2) + ","
		con += cel(r-1,c-2) + ","
		con += cel(r+1,c-2) + "}," + "\n"

open(dst,"w").write(con)






