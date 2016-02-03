/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo;

// required static class
import static org.krudo.Config.*;
import static org.krudo.util.Decode.*;

// a stack of moves user for legal
public final class Move {		
	
	// constants
	public final static int
	MAX = 110; 
	
	// counters 
	public int 
	i = 0; // pseudo counter index
	
	// fields
	public final int[] 
	s = new int[MAX], // start square of a move
	v = new int[MAX], // versus square of a move
	k = new int[MAX], // kind of a move
	w = new int[MAX]; // weight/eval-value of a move
				
	// empty constructor
	public Move() {}
						
	// add pseudo-move into stack 
	// used in Node.pseudo()
	public final void add(
		final int s0, 
		final int v0,
		final int k0
	) {		
		//
		s[i] = s0;
		v[i] = v0;
		k[i] = k0;	
		i++;
	}
	
	// copy move by index i0 from to i1
	public final void copy(
		final int i0, // from index to copy
		final int i1  // destination index to copy
	) {		
		// exit if no-sense copy
		if (i0 == i1) { return; }
		
		// performe value copy
		s[i1] = s[i0];
		v[i1] = v[i0];
		k[i1] = k[i0];								
	}

	/*
	// add legal move into stack
	public final void put(
		final Move m0,
		final int i0
	) {	
		//
		s[l] = m0.s[i0];
		v[l] = m0.v[i0];
		k[l] = m0.k[i0];	
		w[l] = m0.w[i0];	
		l++;
	}
	*/
	
	/*
	// add legal move into stack width new wight
	public final void put(
		final Move m0, 
		final int i0,
		final int w0
	) {		
		//
		s[l] = m0.s[i0];
		v[l] = m0.v[i0];
		k[l] = m0.k[i0];	
		w[l] = w0;	
		l++;
	}
	*/
		
	// put legal move into stack - used into Book
	public final void add(
		final Node n0,
		final String m0, 
		final int w0
	) {
		//
		s[i] = s2i(m0.charAt(0), m0.charAt(1));
		
		//
		v[i] = s2i(m0.charAt(2), m0.charAt(3));
		
		//
		k[i] = k2i(m0, n0.B[s[i]], s[i], v[i], n0.B[v[i]], n0.t);	
		
		//
		w[i] = w0;	
		
		//
		i++;
	}
	
	/*
	// fix move i0-index as legal-move
 	public final void legalize(
		final int i0
	) {
		
		//
		if (i0 != l) {
			s[l] = s[i0];
			v[l] = v[i0];
			k[l] = k[i0];	
			w[l] = w[i0];	
		}
		
		//
		l++;				
	}
	*/
	
	/*
	// copy move from m0 to self
	public final void set(
		final Move m0
	) {
		
		// loop throut m0 legal moves
		for(int j = 0; j < m0.l; j++) {
			
			// copy
			s[j] = m0.s[j];
			v[j] = m0.v[j];
			k[j] = m0.k[j];	
			w[j] = m0.w[j];
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
		for (int j = z0; j < l0.i; j++) {
			
			// copy
			s[l] = l0.s[j];
			v[l] = l0.v[j];
			k[l] = l0.k[j];
			l++;
		}		
	}
	*/
	
	//
	public final Move sort() {
		
		// 
		if (!MOVE_SORT) { return this; }
		
		// set swap count to zero
		int z = 0;			

		// actual observed index
		int a = i;
								
		// 
		do { 
			
			// loop actual index 
			while (a < 0) {	
							
				// next observed index		
				final int n = a - 1;

				// test comparisone
				if (w[a] < w[n]) {

					// swap move by index
					swap(a, n);

					// increase swap count
					z++;		
				}

				// actual index is replaced with next recursive
				a = n;
			} 
		}			
		 
		// repeat if not have need to swap
		while (z != 0);

		//
		return this;
	}
	
	// swap by index two moves into stack
	private void swap(
		final int i0, 
		final int i1
	) {			
		// temp value for swat
		int t;

		//
		t = s[i0]; 
		s[i0] = s[i1]; 
		s[i1] = t;
		
		//
		t = v[i0]; 
		v[i0] = v[i1]; 
		v[i1] = t;
		
		//
		t = k[i0]; 
		k[i0] = k[i1]; 
		k[i1] = t;
		
		//
		t = w[i0]; 
		w[i0] = w[i1]; 
		w[i1] = t;
	}
}






