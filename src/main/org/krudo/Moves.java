
  /*\
 / + \ Krudo 0.20a - the blasphemy chess engine.
 \IHS/ by Francesco Bianco <bianco@javanile.org>
  \*/

//
package org.krudo;

//
public class Moves 
{
    //
    private final static int
    STACK_SIZE = 130000;

    //
    private final static Move[]
    STACK = new Move[STACK_SIZE];
    
    //
    private static int count = 0;
        
    //
    public static void init()
    {
        //
        for (int i = 0; i < STACK_SIZE; i++) 
        {
            STACK[i] = new Move(); 
        }
        
        //
        count = STACK_SIZE;
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
