/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.search;

import org.krudo.Legals;
import static org.krudo.Tool.*;

//
import org.krudo.*;

// 
public class Search2 
{
    //
    public static void main(String[] args) 
    {
        //
        PVs.init();
        
        //
        Moves.init();
        
        // create a node to service the search
        Node n = new Node();
        
        //
        n.startpos();
        
        // create a serach engine based-on the node
        Search s = new Search(n);
        
        //
        s.start(15, 1000);
        
        //
        Legals.info();
        
        //
        //echo(s.eval(7), s.ns, time()-s.timeStart);        
        
        //
        // echo(Move.b.size(),Cache.legals.size());
    }    
}
