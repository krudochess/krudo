/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo;

//
import static org.krudo.Tool.*;

//
public class Moves 
{
    public final static int MOVES_STACK_SIZE = 101000;
    
    //
    private static int count=0;
    
    //
    private final static Move[] STACK = new Move[MOVES_STACK_SIZE];
    
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
    public static void free(final Move move) 
    {
        //
        move.i = 0;
        
        //
        STACK[count++] = move;
    }
    
    //
    public static void info()
    {
        print("Moves free="+count);
    }
}
