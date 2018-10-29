/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.eval;

//
import org.krudo.*;
    
//
import static org.krudo.Tool.*;
import static org.krudo.debug.Walk.*;
    
//
public class Eval5 
{
    //
    public static void main(String[] args) 
    {    
        //
        //DEBUG_SHOW_MOVE_WEIGHT = true;
        
        //
        Moves.init();

        //
        Node n = new Node();

        //
        n.startpos();
        
        //
        debug_walk_book(n, 20);
        
        //
        //dump(n);
        
        //
        print("EvalUtility: "+Eval.node(n));
        
        n.legals();
                
        //
        //dump(n.legals.sort());
        
        //
        //dump(n.L);
    }
}
