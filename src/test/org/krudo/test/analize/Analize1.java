
  /*\
 / + \ Krudo 0.20a - the messianic chess engine.
 \IHS/ by Francesco Bianco <bianco@javanile.org>
  \*/

package org.krudo.test.analize;

import static org.krudo.Tool.*;
import static org.krudo.test.debug.Debug.*;
import static org.krudo.test.debug.Reflect.*;
import static org.junit.jupiter.api.Assertions.*;

import org.krudo.*;
import org.junit.jupiter.api.*;

public class Analize1 
{
    @Test
    void testAnalysis()
    {
        DEBUG_SHOW_ALGEBRIC = true;
        DEBUG_SHOW_MOVE_WEIGHT = true;
              
        Krudo.init();
                
        String fen = "r3nrk1/2p2p1p/p1p1b1p1/2NpPq2/3R4/P1N1Q3/1PP2PPP/4R1K1 w";
                        
        Search s = new Search(fen);

        Inspect.SEARCH_SHOW_INFO.add("id-loop-end");


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
