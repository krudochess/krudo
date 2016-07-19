/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

// 
package org.krudo;

//
import static org.krudo.Debug.*;
import static org.krudo.Tool.*;

//
public class PV
{
    //
    public final int[] s;
    public final int[] v;
    public final int[] k;
    
    //
    public int i;

    //
    public PV()
    {
        s = new int[15];
        v = new int[15];
        k = new int[15];
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
       
        i = 1;
        
        //print("1:");
        //dump(this);
        
        //print("2:");
        //dump(pv);
        
        //
        System.arraycopy(pv.s, 0, s, 1, pv.i);
        System.arraycopy(pv.v, 0, v, 1, pv.i);
        System.arraycopy(pv.k, 0, k, 1, pv.i);
        
        //
        i = pv.i + 1;
        
        //print("=:");
        //dump(this);
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
}
