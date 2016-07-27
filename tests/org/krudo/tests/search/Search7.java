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
import static org.krudo.Describe.*;

// 
public class Search7 
{
    //
    public static void main(String[] args) 
    {
        //
        SEARCH_QUIESCENCE = true;
        
        //
        Krudo.init();
        
        // create a node to service the search
        Node n = new Node();
                    
        //
        dump(n);
        
        // create a serach engine based-on the node
        Search s = new Search(n);
     
        //
        /*
        s.sendinfo = () -> 
        {
            //
            if (s.info_event.equals("id-loop-run")) 
            {
                print(desc(s.best_pv));
            }        
        };
        */
        
        //
        try 
        {
            s.start(2, 50000);
        }
        catch (Exception e)
        {
            dump(n);
            dump(n.L);
            
            e.printStackTrace();
        }
    }    
}
