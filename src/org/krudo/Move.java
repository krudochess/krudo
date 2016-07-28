/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo;

// required static class
import static org.krudo.Config.*;
import static org.krudo.Tool.*;
import static org.krudo.Encode.*;
import static org.krudo.Describe.*;

// a stack of moves user for legal
public final class Move 
{            
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

    //
    public final Move sort() 
    {    
        // 
        if (!MOVE_SORT) { return this; }
        
        // count all swap
        int c = 0; 
        
        // swap count
        int z;            

        // 
        do 
        {                                 
            // set swap count to zero
            z = 0;
            
            //
            for (int j = 1; j < i; j++) if (w[j-1] < w[j]) 
            {
                // swap move by index
                swap(j - 1, j);

                // increase swap count
                z++;    
            }
           
            //
            c += z;            
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
    
    // duplicate a move-stack instance
    public final Move duplicate()
    {
        //
        if (!CACHE_LEGALS) { return this; }
        
        //
        Move m = Moves.pick();
        
        //
        System.arraycopy(s, 0, m.s, 0, i);
        System.arraycopy(v, 0, m.v, 0, i);
        System.arraycopy(k, 0, m.k, 0, i);
        System.arraycopy(w, 0, m.w, 0, i);
          
        //
        m.i = i;
        
        //
        return m;
    }
}






