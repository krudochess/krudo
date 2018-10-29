/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

// 
package org.krudo;

//
import static org.krudo.Tool.*;
import static org.krudo.Config.*;

//
public class PV
{
    //
    private final static int PV_SIZE = 18;
    
    //
    public final int[] s;
    
    //
    public final int[] v;
    
    //
    public final int[] k;
    
    //
    public int i;

    //
    public PV()
    {
        //
        s = new int[PV_SIZE];
        
        //
        v = new int[PV_SIZE];
        
        //
        k = new int[PV_SIZE];
        
        //
        i = 0;
    }
    
    //
    public final void copy(PV pv) 
    {
        //
        System.arraycopy(pv.s, 0, s, 0, pv.i);
        System.arraycopy(pv.v, 0, v, 0, pv.i);
        System.arraycopy(pv.k, 0, k, 0, pv.i);
        
        //
        i = pv.i;
    }
    
    //
    public final void cat(PV pv, Move m, int mi) 
    {
        //
        s[0] = m.s[mi];
        v[0] = m.v[mi];
        k[0] = m.k[mi];
    
        //
        i = 1;
        
        //
        System.arraycopy(pv.s, 0, s, 1, pv.i);
        System.arraycopy(pv.v, 0, v, 1, pv.i);
        System.arraycopy(pv.k, 0, k, 1, pv.i);
        
        //
        i = pv.i + 1;
    }  
   
    //
    public final void cat(PV pv, Capture c, int ci) 
    {
        //
        s[0] = c.s[ci];
        v[0] = c.v[ci];
        k[0] = c.k[ci];
        
        //
        i = 1;
        
        //
        System.arraycopy(pv.s, 0, s, 1, pv.i);
        System.arraycopy(pv.v, 0, v, 1, pv.i);
        System.arraycopy(pv.k, 0, k, 1, pv.i);
        
        //
        i = pv.i + 1;       
    }

    //
    public final void cat(int s0, int v0, int k0)
    {
        //
        s[0] = s0;
        v[0] = v0;
        k[0] = k0;

        //
        i = 1;
    }

    //
    public final void clear()
    {
        i = 0;
    }
}
