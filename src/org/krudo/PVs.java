
  /*\
 / + \ Krudo 0.20a - the blasphemy chess engine.
 \IHS/ by Francesco Bianco <bianco@javanile.org>
  \*/

// krudo package
package org.krudo;

// bucket of pre-created PV stacks
public class PVs
{    
    //
    private final static int
    SIZE = 1000;
    
    //
    private final static
    PV[] STACK = new PV[SIZE];
    
    //
    private static int
    count;
    
    //
    public final static void init()
    {          
        //
        for(int i = 0; i < SIZE; i++)
        {
            STACK[i] = new PV();        
        }
        
        //
        count = SIZE;
    }
    
    // get one stack from bucket
    public static final PV pick()
    {
        //
        return STACK[--count];    
    }
    
    // free one stack and move to bucket
    public static void free(final PV pv) 
    {
        //
        pv.i = 0;
        
        //
        STACK[count++] = pv;
    }
}
