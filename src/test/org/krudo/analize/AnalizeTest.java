
  /*\
 / + \ Krudo 0.20a - the messianic chess engine.
 \IHS/ by Francesco Bianco <bianco@javanile.org>
  \*/

package org.krudo.analize;

import static org.krudo.Tool.*;

import org.krudo.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.krudo.Parse.*;

class AnalizeTest
{
    @Test
    void testAnalysis()
    {


        assertEquals(parse_square("e4"), 28);
        /*
        DEBUG_SHOW_ALGEBRIC = true;
        DEBUG_SHOW_MOVE_WEIGHT = true;
              
        Krudo.init();
                
        String fen = "r3nrk1/2p2p1p/p1p1b1p1/2NpPq2/3R4/P1N1Q3/1PP2PPP/4R1K1 w";
                        
        Search s = new Search(fen);

        Inspect.SEARCH_INFO_SHOW.add("id-loop-end");

        //s.node.domove("h6g6");
        //s.node.domove("h7g6");
        //s.node.domove("g1g6"); // f7g7 g6g7 g8f8 h1h8
        //s.node.domove("f7g7");
        
        print("EvalUtility:", Eval.node(s.node));
        
        //s.node.legals();  
        //dump(s.node);
        //dump(s.node.legals.sort());
        
        s.start(20, 30000);
        */
    }    
}
