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
        DEBUG_SHOW_MOVE_WEIGHT = true;
        
        //
        Krudo.init();
        
        // create a node to service the search
        Node n = new Node();
        
        //
        n.startpos("8/7p/5k2/5p2/p1p2P2/Pr1pPK2/1P1R3P/8 b");
        
                
        //
        dump(n);
        
        // create a serach engine based-on the node
        Search s = new Search(n);
     
        n.legals();
               
        
        dump(n.legals.sort());
        
        //
        try {
            s.start(14, 5000000);
        } catch (Exception e) {
            dump(n);        
            dump(n.L);   
            n.legals();
            dump(n.legals);     
            e.printStackTrace();            
        }
    }    
}
