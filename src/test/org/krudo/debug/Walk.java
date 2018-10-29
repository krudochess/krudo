
  /*\
 / + \ Krudo 0.20a - the messianic chess engine.
 \IHS/ by Francesco Bianco <bianco@javanile.org>
  \*/

package org.krudo.debug;

import org.krudo.*;

import java.io.IOException;

public class Walk
{
 /*   //
    public static final void debug_walk_book(final Node n, final int u)
    {
        //
        Strings m = null;

        m = Book.list(n.phk);

        //
        if (m.isEmpty()) { return; }

        //
        n.domove(m.get(u % m.size()));

        //
        debug_walk_book(n, u / m.size());
    }

    *//*
    // WALK EVAL
    public final static void walk(final Node n, int deep, int width)
    {
        //
        if (deep == 0) {
            //dump(n);
            return;
        }
       
        //
        n.legals();
        
        //
        Move m = n.legals.sort().twin();
    
        //
        int w = m.count > width ? width : m.count;
        
        //
        for (int i = 0; i < w; i++) 
        {
            n.domove(m, i);
            
            walk(n, deep - 1, width);
            
            n.unmove();
            
            if (deep == 1) {
                //dump(m);
                print("\n");
            }
        }
        
        Moves.free(m);
    }
     *//*

    //
    public final static void walk(final Node n, int depth, int width)
    {
        //
        if (depth == 0) { return; }

        //
        n.legals();

        //
        Move m = n.legals.sort();

        //
        int w = m.count > width ? width : m.count;

        //
        for (int i = 0; i < w; i++)
        {
            //
            n.domove(m, i);

            //
            walk(n, depth - 1, width);

            //
            n.unmove();
        }

        //
        Moves.free(m);
    }
*/
}
