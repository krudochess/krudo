/**
 * Krudo 0.18a - Java chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo;

//
public class Moves 
{
    //
    private final static int MOVES_STACK_SIZE = 270000;

    //
    private final static Move[] STACK = new Move[MOVES_STACK_SIZE];
    
    //
    private static int count = 0;
        
    //
    public static void init()
    {
        //
        for (int i = 0; i < MOVES_STACK_SIZE; i++) 
        {
            STACK[i] = new Move(); 
        }
        
        //
        count = MOVES_STACK_SIZE;
    }
    
    //
    public static Move pick() 
    {
        //
        return STACK[--count];
    }
    
    //
    public static void free(
        final Move move
    ) {
        //
        move.count = 0;
        
        //
        STACK[count++] = move;
    }
}
