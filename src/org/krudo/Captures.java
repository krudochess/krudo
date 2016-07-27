/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo;

//
import static org.krudo.Tool.*;

//
public class Captures 
{
    //
    public final static int CAPTURES_STACK_SIZE = 1000;
    
    //
    private static int count = 0;
    
    //
    private final static Capture[] STACK = new Capture[CAPTURES_STACK_SIZE];
    
    //
    public static void init()
    {
        //
        for (int i = 0; i < CAPTURES_STACK_SIZE; i++)
        {
            STACK[i] = new Capture(); 
        }
        
        //
        count = CAPTURES_STACK_SIZE;
    }
    
    //
    public static Capture pick() 
    {
        //
        return STACK[--count];
    }
    
    //
    public static void free(final Capture capture) 
    {
        //
        capture.i = 0;
        
        //
        STACK[count++] = capture;
    }
    
    //
    public static void info()
    {
        print("Capture free="+count);
    }
}
