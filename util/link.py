
import sys,os

dst = sys.argv[1]

con = "";

def cell(r,c):
	if 0 <= r and r < 8 and 0 <= c and c < 8:
		return chr(ord("a")+c)+chr(ord("1")+r)
	else:
		return "xx"

def orto(r0,c0,r1,c1):
    if r0 == r1 or c0 == c1: return 0 
    return 1
	
def diag(r0,c0,r1,c1):
	if c0+r0 == c1+r1: return 0
	if c0-r0 == c1-r1: return 0
	return 1;	
	 
def hope(r0,c0,r1,c1):
    if abs(r0-r1) == 2 and abs(c0-c1) == 1: return 0
    if abs(r0-r1) == 1 and abs(c0-c1) == 2: return 0
    return 1

def mall(r0,c0,r1,c1):
	if orto(r0,c0,r1,c1) == 0 or diag(r0,c0,r1,c1) == 0 or hope(r0,c0,r1,c1) == 0: return 1
	return 0
										
for r0 in range(0,8):
	for c0 in range(0,8):
		con += "/*"+cell(r0,c0)+":*/{"				
		for r1 in range(0,8):
			for c1 in range(0,8):
				val = mall(r0,c0,r1,c1)
				con += str(val) + ","
		con += "}," + "\n"
		
open(dst,"w").write(con)

