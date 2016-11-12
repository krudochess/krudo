/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

// 
package org.krudo;

//
import static org.krudo.Tool.*;
import static org.krudo.Debug.*;
import static org.krudo.Config.*;

//
public final class TT 
{       
    //
    public final static int 
    SIZE = 500000,
    A = 1,
    B = 2,
    E = 3;
        
    //
    private final static ROW[] TABLE = new ROW[SIZE]; 
                   
    //
    private static class ROW 
    {
        //
        public long hash; 

        //
        public int depth; 

        //
        public int score;
        
        //
        public int flag;
        
        //
        public int s;

        //
        public int v;
    }
    
    //
    public final static void init()
    {        
        //
        for (int i = 0; i < SIZE; i++)
        {
            TABLE[i] = new ROW();        
        }
    }
            
    //
    public final static boolean probe(long h, int d, int a, int b) 
    {
        //
        return false;
    }
        
    //
	public final static void store(long h, int f, int d, int s) 
    {	
        //
        final ROW r = TABLE[(int)(h & SIZE)];
        
        //
        if (r.hash == h && r.depth > d) { return; } 
        
        //
        r.hash = h;
        
        //
        r.flag = f;
        
        //
        r.depth = d;
        
        //
        r.score = s;
                
        //
        //phashe->bestmove = best;
	}
}
