/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.promotion;

//
import org.krudo.Node;
import org.krudo.Search;

//
import static org.krudo.Tool.*;

// 
public class Promotion1 
{
    //
    public static void main(String[] args) 
    {              
        // create a node to service the search
        Node n = new Node();
        
        //
        n.startpos("k7/8/8/8/8/8/p6P/7K b");
        
        // create a serach engine based-on the node
        Search s = new Search(n);
            
        //
        s.start(4);
    }    
}
