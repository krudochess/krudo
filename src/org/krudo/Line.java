/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo;

// class for contain move-hystory or move sequence in game
public final class Line {    

    // internal constants
    private final static int 
    MAX = 300; // maximum number of half move into line
    
    // stack move fields 
    public final int[] 
    p, // moved piece
    s, // start square
    v, // versus square
    x, // captured piece
    k, // kind-of-move
    e, // en-passant square
    c; // castling status
    
    //
    public final long[]
    h; //
    
    //
    public int i;
    
    // constuctor
    public Line() 
    {    
        // initialization of memory 
        p = new int[MAX];
        s = new int[MAX];
        v = new int[MAX];
        x = new int[MAX];
        k = new int[MAX];
        e = new int[MAX];    
        c = new int[MAX];        
        h = new long[MAX];        
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
        final int c0,
        final long h0
    ) {
        // put and next record
        p[i0] = p0;        
        s[i0] = s0;
        v[i0] = v0;
        x[i0] = x0;
        k[i0] = k0;
        e[i0] = e0;
        c[i0] = c0;   
        h[i0] = h0;   
        i = i0;
    }        
}
