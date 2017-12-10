package org.krudo.tests.winatchess;

//
import org.krudo.*;

//
import static org.krudo.Eval.*;
import static org.krudo.Tool.*;
import static org.krudo.tests.debug.Debug.*;

// 
public class Winatchess2 {

    //
    public static void main(String[] args) {
        
        // 1r1rb1k1/2p3pp/p2q1p2/3PpP1Q/Pp1bP2N/1B5R/1P4PP/2B4K w - - bm Qxh7+; id "WAC.185";
        // r5r1/p1q2p1k/1p1R2pB/3pP3/6bQ/2p5/P1P1NPPP/6K1 w - - bm Bf8+; id "WAC.186";
        // 6k1/5p2/p3p3/1p3qp1/2p1Qn2/2P1R3/PP1r1PPP/4R1K1 b - - bm Nh3+; id "WAC.187";

        DEBUG_SHOW_MOVE_WEIGHT = true;
        
        Krudo.init();
        
        Search s = new Search("1r1rb1k1/2p3pp/p2q1p2/3PpP1Q/Pp1bP2N/1B5R/1P4PP/2B4K w");
        
        dump(s.node);

        s.node.legals();
        
        dump(s.node.legals);
        
        print("-");
        
        s.node.captures();
        
        dump(s.node.captures);
        
        
        s.start(5);
    }    
}
