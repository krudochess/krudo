/**
 * Krudo 0.18a - Java chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.moves;

// 
import org.krudo.Move;
import org.krudo.Moves;

//
import static org.krudo.tests.debug.Info.*;
import static org.krudo.tests.debug.Reflect.*;

// 
public class Moves1 
{
    //
    public static void main(String[] args)
    {        
        //
        Moves.init();
        
        //
        int l = get_field_value_as_int(Moves.class, "STACK_SIZE");
        Move[] temp = new Move[l];
        
        //
        for (int i = 0; i < l; i++) {
            temp[i] = Moves.pick();
        }   
        info_moves();
        
        //
        for (int i = 0; i < l; i++) {
            Moves.free(temp[i]);
        }        
        info_moves();
    }    
}
