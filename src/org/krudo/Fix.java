/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo;

// required static class
import static org.krudo.Config.*;
import static org.krudo.Constant.*;
import static org.krudo.Tool.*;
import static org.krudo.Encode.*;
import static org.krudo.Describe.*;

// a stack of moves user for legal
public final class Fix 
{            
    //
    public static final int fix_castling_versus_square(int s, int v, int k)
    {
        if (s == e1 && k == KSCA) { return g1; }
        if (s == e1 && k == QSCA) { return c1; }
        if (s == e8 && k == KSCA) { return g8; }
        if (s == e8 && k == QSCA) { return c8; }
        return v;        
    }
        




}







