/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo;

// class for contain move-hystory or move sequence in game
public final class Line 
{    
    // internal constants
    private final static int 
    MAX = 500; // maximum number of half move into line
    
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
        i = 0;
    }
    
    // add move into stack 
    public final void store(
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
        p[i] = p0;        
        s[i] = s0;
        v[i] = v0;
        x[i] = x0;
        k[i] = k0;
        e[i] = e0;
        c[i] = c0;   
        h[i] = h0;   
        i++;
    }        
}
