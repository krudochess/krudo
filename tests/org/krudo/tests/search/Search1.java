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
import static org.krudo.Config.*;

// 
public class Search1 
{
    //
    public static void main(String[] args)
    {
        // dobbiamo migliorare il tempo di questo BRUTE_FORCE senza quiescenza
        // id-loop-end 5/5 e2e4 e7e6 f1c4 e6e5 c4f7 11185ms 4865609n 435knps
                
        //
        SEARCH_CONTROL = true;
        
        //
        SEARCH_QUIESCENCE = true;
        
        //
        SEARCH_BRUTE_FORCE = false;
                
        //
        Krudo.init();
                
        // create a serach engine based-on the node
        Search s = new Search();
        
        //
        s.event_filter.add("ab-control-speed");
        s.event_filter.add("ab-routine-end");
        s.event_filter.add("id-loop-break");
                
        //
        s.start(10, 100000);                              
    }    
}
