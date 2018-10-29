/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.test.eval;

//
import org.krudo.*;
    
//
import static org.krudo.Tool.*;
import static org.krudo.test.debug.Debug.*;
    
//
public class Eval6 
{
    //
    public static void main(String[] args) 
    {    
        //
        DEBUG_SHOW_MOVE_WEIGHT = true;
        
        //
        Eval.init();
        
        //
        Moves.init();

        //
        Node n = new Node();

        //
        n.startpos("kb6/8/8/8/8/8/8/7K w");
        
        //
        //dump(n);
        
        //
        print("save: "+Eval.node(n));
    }
}
