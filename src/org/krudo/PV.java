/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

// 
package org.krudo;

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
        s = new int[10];
        v = new int[10];
        k = new int[10];
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
    public final void copy(PV pv, Move m, int mi) 
    {
        //
        s[0] = m.s[mi];
        v[0] = m.v[mi];
        k[0] = m.k[mi];
        
        //
        System.arraycopy(pv.s, 1, s, 0, pv.i);
        System.arraycopy(pv.v, 1, v, 0, pv.i);
        System.arraycopy(pv.k, 1, k, 0, pv.i);
        
        //
        i = pv.i + 1;
    }  
   
    //
    public final void copy(PV pv, Capture c, int ci) 
    {
        //
        s[0] = c.s[ci];
        v[0] = c.v[ci];
        k[0] = c.k[ci];
        
        //
        System.arraycopy(pv.s, 1, s, 0, pv.i);
        System.arraycopy(pv.v, 1, v, 0, pv.i);
        System.arraycopy(pv.k, 1, k, 0, pv.i);
        
        //
        i = pv.i + 1;       
    }     
}
