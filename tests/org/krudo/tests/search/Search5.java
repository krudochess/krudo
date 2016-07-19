/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.search;

import org.krudo.Moves;
import static org.krudo.Tool.*;

//
import org.krudo.Node;
import org.krudo.Search;

// 
public class Search5 
{
    //
    public static void main(String[] args) 
    {
        //
        Moves.init();
        
        // create a node to service the search
        Node n = new Node();
        
        //
        n.startpos();
        
        // create a serach engine based-on the node
        Search s = new Search(n);
     
        //
        s.start(6);       
    }    
}
