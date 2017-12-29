
  /*\
 / + \ Krudo 0.20a - the blasphemy chess engine.
 \IHS/ by Francesco Bianco <bianco@javanile.org>
  \*/

//
package org.krudo;

//
import static org.krudo.Config.*;

//
public class Moves 
{
    //
    private final static int
    SIZE = (MEMORY_BUFFER << MEMORY_FACTOR);

    //
    private final static Move[]
    STACK = new Move[SIZE];
    
    //
    private static int count = 0;
        
    //
    public static void init()
    {
        //
        for (int i = 0; i < SIZE; i++)
        {
            STACK[i] = new Move();
        }
        
        //
        count = SIZE;
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
