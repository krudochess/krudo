import sys,os

dst = sys.argv[1]

con = "";


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

def cell(r,c):
	if 0 <= r and r < 8 and 0 <= c and c < 8:
		return chr(ord("a")+c)+chr(ord("1")+r)
	else:
		return "xx"

def hipe(r0,c0,r1,c1):
	return str(knightDistance(r0*8+c0,r1*8+c1));
	
for r0 in range(0,8):
	for c0 in range(0,8):
		con += "/*"+cell(r0,c0)+":*/{"				
		for r1 in range(0,8):
			for c1 in range(0,8):
				con += hipe(r0,c0,r1,c1) + ","				  
		con += "}," + "\n"		
		
open(dst,"w").write(con)






