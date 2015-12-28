package org.krudo;

// class for contain move-hystory or sequence in game
public final class Line {	

	// internal constants
	private final static int 
	LINE_SIZE	= 300;	// maximum number of half move into line
	
	// stack move fields 
	public final int[] 
	p,	// piece
	s,	// start square
	v,	// versus square
	x,	// captured piece
	k,	// kind-of-move
	e,	// en-passant square
	c;	// castling status
	
	// constuctor
	public Line() {
		
		// initialization of memory 
		p = new int[LINE_SIZE];
		s = new int[LINE_SIZE];
		v = new int[LINE_SIZE];
		x = new int[LINE_SIZE];
		k = new int[LINE_SIZE];
		e = new int[LINE_SIZE];	
		c = new int[LINE_SIZE];		
	}
	
	// add move into stack 
	public final void store(
		final int i0,
		final int p0, 
		final int s0, 
		final int v0, 
		final int x0, 
		final int k0, 
		final int e0, 
		final int c0
	) {
		
		// put and next record
		p[i0] = p0;		
		s[i0] = s0;
		v[i0] = v0;
		x[i0] = x0;
		k[i0] = k0;
		e[i0] = e0;
		c[i0] = c0;		
	}		
}
