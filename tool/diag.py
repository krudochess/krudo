import sys,os

dst = sys.argv[1]

con = "";

def cell(r,c):
	if 0 <= r and r < 8 and 0 <= c and c < 8:
		return chr(ord("a")+c)+chr(ord("1")+r)
	else:
		return "xx"

def diag(r0,c0,r1,c1):
	if c0+r0 == c1+r1: return "1"
	elif c0-r0 == c1-r1: return "1"
	elif (c0+r0)%2 == (c1+r1)%2: return "2"
	return "9";
	
for r0 in range(0,8):
	for c0 in range(0,8):
		con += "/*"+cell(r0,c0)+":*/{"				
		for r1 in range(0,8):
			for c1 in range(0,8):
				con += diag(r0,c0,r1,c1) + ","				  
		con += "}," + "\n"		
		
open(dst,"w").write(con)






