/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.analize;

import org.krudo.Legals;
import static org.krudo.Tool.*;
import static org.krudo.Debug.*;
import static org.krudo.Constant.*;

//
import org.krudo.*;
import org.krudo.Move;
import org.krudo.Moves;
import org.krudo.Search;

// 
public class Analize1 
{
    //
    public static void main(String[] args) 
    {
        //
        DEBUG_SHOW_ALGEBRIC = true;

        //
        DEBUG_SHOW_MOVE_WEIGHT = true;
              
        //
        Krudo.init();
                
        //
        String fen = readfile("./console/analize.fen");
                        
        // create a serach engine based-on the node
        Search s = new Search(fen);
          
        //s.node.domove("h6g6");
        //s.node.domove("h7g6");
        
        print("Eval:", Eval.node(s.node));
        
        s.node.legals();
        
        dump(s.node.legals.sort());
        
        //
        s.start(20, 200000);                
    }    
}
