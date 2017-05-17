/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

// 
package org.krudo;

//
import static org.krudo.Tool.*;
import static org.krudo.debug.Debug.*;

//
public class PVs
{    
    //
    private final static int PV_STACK_SIZE = 1000;
    
    //
    private final static PV[] STACK = new PV[PV_STACK_SIZE];;
    
    //
    private static int count;
    
    //
    public final static void init()
    {          
        //
        for(int i = 0; i < PV_STACK_SIZE; i++)
        {
            STACK[i] = new PV();        
        }
        
        //
        count = PV_STACK_SIZE;
    }
    
    //
    public static final PV pick()
    {
        //
        return STACK[--count];    
    }
    
    //
    public static void free(final PV pv) 
    {
        //
        pv.i = 0;
        
        //
        STACK[count++] = pv;
    }
    
    //
    public static void info()
    {
        //
        print(
            "PV free",
            count, 
            count == PV_STACK_SIZE ? "(PERFECT!!)" : "(PROBLEM??)"
        );
    }
}
