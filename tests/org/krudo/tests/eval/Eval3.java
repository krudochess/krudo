/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.eval;

//
import org.krudo.tests.legals.*;
import org.krudo.*;

//
import static org.krudo.debug.Debug.*;
import static org.krudo.Tool.*;
    
//
public class Eval3 
{
    //
    public static void main(String[] args) 
    {    
        //
        DEBUG_SHOW_MOVE_WEIGHT = true;
        
        //
        Moves.init();

        //
        Node n = new Node();

        //
        n.startpos("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/1NBQKBNR w KQkq - 0 1");
        
        //
        dump(n);
        
        n.legals();
                
        //
        dump(n.legals.sort());
        
        //
        n.domove("b1a3");
        
        //
        print("");
        
        //
        dump(n);

        //
        n.legals();
                
        //
        dump(n.legals.sort());
        
        //
        print("");
        
        //
        //Eval.dump();
    }
}
