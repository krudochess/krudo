/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.promotion;

//
import org.krudo.*;

//
import static org.krudo.tests.debug.Debug.*;

// 
public class Promotion2 
{
    //
    public static void main(String[] args) 
    {              
        //
        Moves.init();

        // create a node to service the search
        Node n = new Node();
        
        //
        n.startpos("k7/8/8/8/8/8/p6P/7K b");
        
        //
        dump(n);
        
        //
        //dump(n.legals());
    }    
}
