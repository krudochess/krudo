/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

package org.krudo.tests.analize;

import org.krudo.*;
import org.krudo.Legals;

import static org.krudo.Tool.*;
import static org.krudo.tests.debug.Debug.*;
import static org.krudo.Constants.*;

public class Analize1 
{
    public static void main(String[] args) 
    {
        DEBUG_SHOW_ALGEBRIC = true;
        DEBUG_SHOW_MOVE_WEIGHT = true;
              
        Krudo.init();
                
        String fen = readfile("./console/analize.fen");
                        
        Search s = new Search(fen);
          
        SEARCH_EVENT_FILTER.add("id-loop-end");
        
        //s.node.domove("h6g6");
        //s.node.domove("h7g6");
        //s.node.domove("g1g6"); // f7g7 g6g7 g8f8 h1h8
        //s.node.domove("f7g7");
        
        print("Eval:", Eval.node(s.node));
        
        //s.node.legals();  
        //dump(s.node);
        //dump(s.node.legals.sort());
        
        s.start(20, 30000);                
    }    
}
