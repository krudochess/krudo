/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.promotion;

//
import org.krudo.*;

//
import static org.krudo.Debug.*;

// 
public class Promotion3 
{
    //
    public static void main(String[] args) 
    {              
        //
        Moves.init();

        // create a node to service the search
        Node n = new Node();
        
        //
        n.startpos("8/7P/8/8/8/8/k7/7K w");
        
        //
        n.domove("h7h8q");
        
        //
        n.domove("a2a3");
        
        //
        dump(n);
                        
        
        //
        n.legals();
        
        //
        dump(n.legals);
    }    
}
