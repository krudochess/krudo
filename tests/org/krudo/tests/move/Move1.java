/**
 * Krudo 0.18a - Java chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.move;

// 
import org.krudo.*;

//
import static org.krudo.debug.Info.*;

// 
public class Move1 
{
    //
    public static void main(String[] args) 
    {
        Moves.init();
        
        Move m0 = Moves.pick();        
        Move m1 = m0.twin();
        
        info_moves();
        
        Moves.free(m0);
        Moves.free(m1);

        info_moves();
    }    
}
