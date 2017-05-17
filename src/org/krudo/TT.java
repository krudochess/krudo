/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

// 
package org.krudo;

//
import static org.krudo.Tool.*;
import static org.krudo.debug.Debug.*;
import static org.krudo.Config.*;

//
public final class TT 
{       
    //
    public final static int 
    SIZE = (int) Math.pow(2, 19) - 1,
    A = 1,
    B = 2,
    E = 3;
        
    //
    private final static ROW[] TABLE = new ROW[SIZE + 1]; 
                   
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
        public int bm_s;

        //
        public int bm_v;
    }
    
    //
    public final static void init()
    {        
        //
        for (int i = 0; i <= SIZE; i++)
        {
            TABLE[i] = new ROW();        
        }
    }
            
    //
    public final static boolean probemax(long h) 
    {
        //
        final ROW r = TABLE[(int)(h & SIZE)];
        
        //
        if (r.hash == h) {
           // print("probemax found");            
        }
        
        //
        return false;
    }
                
    //
    public final static boolean probemin(long h) 
    {
        //
        final ROW r = TABLE[(int)(h & SIZE)];
        
        if (r.hash == h) {
            //print("probemin found");
            
        }
        
        //
        return false;
    }
    
    //
	public final static void store(long h, int f, int d, int s, int bm_s, int bm_v) 
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
        r.bm_s = bm_s;

        //
        r.bm_v = bm_v;
	}
}
