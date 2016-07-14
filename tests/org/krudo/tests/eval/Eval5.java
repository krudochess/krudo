/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.eval;

//
import org.krudo.*;
    
//
import static org.krudo.util.Debug.*;
    
//
public class Eval5 
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
        n.startpos();
        
        //
        Book.walk(n, 1);
        
        //
        dump(n);
        
        //
        dump(n.legals());
        
        //
        dump(n.L);
    }
}
