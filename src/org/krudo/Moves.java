/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo;

import static org.krudo.util.Tool.*;

//
public class Moves 
{
    //
    private static int count=0;
    
    //
    private final static Move[] STACK = new Move[100];
    
    //
    public static void init()
    {
        for (int i=0; i<100; i++)
        {
            STACK[i] = new Move(); 
        }
        count = 100;
    }
    
    //
    public static Move pick() 
    {
        //if (count == 95) {
        //    print ("90%");
        //}
        
        return STACK[--count];
    }
    
    //
    public static void free(final Move move) 
    {
        //
        move.i = 0;
        
        //
        STACK[count++] = move;
    }
}
