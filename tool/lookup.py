import sys,os

dst = sys.argv[1]

con = "";

def cell(r,c):
	if 0 <= r and r < 8 and 0 <= c and c < 8:
		return chr(ord("a")+c)+chr(ord("1")+r)
	else:
		return "xx"

def orto(r0,c0,r1,c1):
	return min(abs(r0-r1),abs(c0-c1))
	
def diag(r0,c0,r1,c1):
	if c0+r0 == c1+r1: return 0
	elif c0-r0 == c1-r1: return 0
	elif (c0+r0)%2 == (c1+r1)%2: return 1
	return 7;	
	
def near(r0,c0,r1,c1):
	n = max(abs(r0-r1),abs(c0-c1))
	return n-1 if n>0 else 0

ndis = [
   0, 3, 2, 3, 2, 3, 4, 5,
   3, 2, 1, 2, 3, 4, 3, 4,
   2, 1, 4, 3, 2, 3, 4, 5,
   3, 2, 3, 2, 3, 4, 3, 4,
   2, 3, 2, 3, 4, 3, 4, 5,
   3, 4, 3, 4, 3, 4, 5, 4,
   4, 3, 4, 3, 4, 5, 4, 5,
   5, 4, 5, 4, 5, 4, 5, 6
]
 
corner  = [ 
   1, 0, 0, 0, 0, 0, 0, 1,
   0, 0, 0, 0, 0, 0, 0, 0,
   0, 0, 0, 0, 0, 0, 0, 0,
   0, 0, 0, 0, 0, 0, 0, 0,
   0, 0, 0, 0, 0, 0, 0, 0,
   0, 0, 0, 0, 0, 0, 0, 0,
   0, 0, 0, 0, 0, 0, 0, 0,
   1, 0, 0, 0, 0, 0, 0, 1
]
 
def absRankFileDiff(a, b): 
	rd = (a|7) - (b|7);
	fd = (a&7) - (b&7);
	return abs(rd) + abs(fd);
 
def knightDistance(a, b): 
	c = absRankFileDiff(a,b)
	d = ndis[c];
	if c == 9: d += 2*(corner[a] ^ corner[b])
	return d

def hipe(r0,c0,r1,c1):
	d = knightDistance(r0*8+c0,r1*8+c1)
	return d-1 if d>0 else 0	
	
for r0 in range(0,8):
	for c0 in range(0,8):
		con += "/*"+cell(r0,c0)+":*/{"				
		for r1 in range(0,8):
			for c1 in range(0,8):
				val = orto(r0,c0,r1,c1)
				val|= diag(r0,c0,r1,c1)<<3
				val|= hipe(r0,c0,r1,c1)<<6
				val|= near(r0,c0,r1,c1)<<9
				con += str(val) + ","				  
		con += "}," + "\n"		
		
open(dst,"w").write(con)






