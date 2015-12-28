package org.krudo;

// required static class
import static org.krudo.Const.*;
import static org.krudo.util.Trans.*;
import static org.krudo.util.Tools.*;

//
import java.util.LinkedList;

// a stack of moves user for legal
public final class Move {	

	// counters 
	public int i; // pseudo counter index
	public int l; // legals counter index
	public int n; // looper coutenr index
	public int c; // is cache

	// zobrist hash
	public long h; 
	
	// fields
	public final int[] s; // start square of a move
	public final int[] v; // versus square of a move
	public final int[] k; // kind of a move
	public final int[] w; // weight/eval-value of a move
	
	// cursors and temp;
	private int t;
	private int z;
	private int j;
		
	// move buffer to avoid call new Move() in runtime
	public static final LinkedList<Move> b;
	
	// static preparation
	static {

		// construct buffer   
		b = new LinkedList<>();

		//
		if (MOVE_BUFFER) {
		
			// fill buffer with pre-constructed moves-stack
			for(int i=0; i<MOVE_BUFFER_SIZE; i++) {			
				b.add(new Move());			
			}
		}
	}
		
	// constructor
	public Move() {
		s = new int[MOVE_SIZE];
		v = new int[MOVE_SIZE];
		k = new int[MOVE_SIZE];
		w = new int[MOVE_SIZE];
		i = 0;
		l = 0;
		n = 0;
	}
	
	//
	public static final Move pop() {
		
		// get a move-stack from buffer
		if (MOVE_BUFFER) {		
			//return b.size() > 0 ? b.poll().clr() : new Move();
			return b.poll().clr();
		} 
		
		// generate empty move-stack
		else {
			return new Move();
		}
	}
	
	//
	public static final void psh(
		final Move m
	) {
		
		//
		if (MOVE_BUFFER) {
			b.add(m);
		}
	}
		
	//
	public final Move clr() {
	
		// empty illega counter
		i = 0;
		
		// emptu legals counter
		l = 0;
		
		// empty loop counter
		n = 0;
		
		//
		return this;
	}
		
	// add pseudo-move into stack - used in Node.pseudo()
	public final void add(
		final int s0, 
		final int v0,
		final int k0
	) {
		s[i] = s0;
		v[i] = v0;
		k[i] = k0;	
		i++;
	}

	// add legal move into stack
	public final void put(
		final Move m0,
		final int i0
	) {
		s[l] = m0.s[i0];
		v[l] = m0.v[i0];
		k[l] = m0.k[i0];	
		w[l] = m0.w[i0];	
		l++;
	}
	
	// add legal move into stack width new wight
	public final void put(
		final Move m0, 
		final int i0,
		final int w0
	) {
		s[l] = m0.s[i0];
		v[l] = m0.v[i0];
		k[l] = m0.k[i0];	
		w[l] = w0;	
		l++;
	}
	
	// put legal move into stack - used into Book
	public final void put(
		final Node n0,
		final String m0, 
		final int w0
	) {
		s[l] = s2i(m0.charAt(0),m0.charAt(1));
		v[l] = s2i(m0.charAt(2),m0.charAt(3));
		k[l] = k2i(m0,n0.B[s[l]],s[l],v[l],n0.B[v[l]],n0.T);	
		w[l] = w0;	
		l++;
	}
	
	// fix move i0-index as legal-move
 	public final void fix(
		final int i0
	) {
		if (i0 != l) {
			s[l] = s[i0];
			v[l] = v[i0];
			k[l] = k[i0];	
			w[l] = w[i0];	
		}
		l++;				
	}
			
	// copy move from m0 to self
	public final void set(
		final Move m0
	) {
		
		// loop throut m0 legal moves
		for(t=0; t<m0.l; t++) {
			
			// copy
			s[t] = m0.s[t];
			v[t] = m0.v[t];
			k[t] = m0.k[t];	
			w[t] = m0.w[t];
		}
		
		// set legal counter to t 
		l = t;
	}
	
	// copy move from m0 to self
	public final void set(
		final Line l0,
		final int z0	
	) {
		
		//
		l = 0;
		
		// loop throut m0 legal moves
		for(t=z0; t<l0.i; t++) {
			
			// copy
			s[l] = l0.s[t];
			v[l] = l0.v[t];
			k[l] = l0.k[t];
			l++;
		}		
	}
	
	//
	public final void loop() {
		
		//
		n++;
	}
	
	//
	public final void stop() {
		
		//
		n--;
				
		//
		if (MOVE_BUFFER && n == 0 && c == 0) {			
			psh(this);
		}		
	}
		
	//
	public final Move sort() {
		
		// 
		if (MOVE_SORT) do {

			// set swap count to zero
			z = 0;			

			//
			for(j=1; j<l; j++) {	
				
				// 
				if (w[j-1] < w[j]) {
					
					// swap move by index
					swap(j-1,j);
					
					// increase swap count
					z++;		
				}
			}				
		} 

		// repeat if not have need to swap
		while(z > 0);

		//
		return this;
	}
	
	// swap by index two moves into stack
	private void swap(int x0, int x1) {
		
		//
		t = s[x0]; s[x0] = s[x1]; s[x1] = t;
		
		//
		t = v[x0]; v[x0] = v[x1]; v[x1] = t;
		
		//
		t = k[x0]; k[x0] = k[x1]; k[x1] = t;
		
		//
		t = w[x0]; w[x0] = w[x1]; w[x1] = t;
	}	
}






