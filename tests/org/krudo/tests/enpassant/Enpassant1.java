/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.enpassant;

//
import org.krudo.*;

//
import static org.krudo.Tool.*;
import static org.krudo.Debug.*;

// 
public class Enpassant1 
{
    //
    public static void main(String[] args) 
    {              
        //
        Moves.init();

        // create a node to service the search
        Node n = new Node();
        
        //
        n.startpos("4k3/pppppppp/8/8/8/8/PPPPPPPP/4K3 w");
                
        //
        n.domove(split("e2e4 c7c5 e4e5 d7d5 e5d6 c5c4 b2b4 e7d6 a2a3"));
        
        //
        //n.unmove();
        
        //
        dump(n);
                                
        //
        n.legals();
        
        //
        dump(n.legals);
    }    
}
