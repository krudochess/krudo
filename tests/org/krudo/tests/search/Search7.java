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
                
        // create a serach engine based-on the node
        Search s = new Search();

        //
        s.node.domove(split("e2e4 c7c5 d2d4 c5d4 d1d4 b8c6 d4d3 g8f6 g1f3 d7d5 e4e5 f6g4 c1f4 d8b6 d3b5 b6f2 e1d1 d5d4 f4d2 g4e3 d1c1 e3f1 h1f1 f2g2 f3d4 a7a6 b5e2 g2e2 d4e2 c6e5 b1c3 c8h3 f1f4"));

        dump(s.node);
        
        s.node.legals();
        
        dump(s.node.legals);
        
        s.node.domove(9);
        
        dump(s.node);
        
        
        exit();
        
        //
        s.sendinfo = () -> 
        {
            //
            if (s.event.equals("id-loop-end")) 
            {
                print(desc(s.id_best_pv));
            }        
        };       
        
        //
        try 
        {
            s.start(8, 5000000);
        }
        
        //
        catch (Exception e)
        {
            //
            dump(s.node);
            
            //
            dump(s.node.L);
            
            //
            e.printStackTrace();
        }
    }    
}
