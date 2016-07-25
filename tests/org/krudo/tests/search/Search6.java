/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.search;

//
import org.krudo.*;

//
import static org.krudo.Tool.*;
import static org.krudo.Debug.*;

// 
public class Search6 
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

        //
        n.domove("e2e4 g8f6 e4e5 f6g8 g1f3 b8c6 b1c3 d7d6 e5d6 c7d6 f1c4 c8e6 c4e2 d8d7 e1g1 e8c8 d2d4 e6g4 c1f4 g8f6 f1e1 d7f5 d1d2 e7e5".split("\\s"));
        
        //
        dump(n);
        
        // create a serach engine based-on the node
        Search s = new Search(n);
     
        //
        try {
            s.start(10, 5000);
        } catch (Exception e) {
            dump(n);        
            dump(n.L);     
            dump(n.legals());     
            e.printStackTrace();            
        }
    }    
}
