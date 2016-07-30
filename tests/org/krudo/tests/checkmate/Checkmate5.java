/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

// 
package org.krudo.tests.checkmate;

//
import org.krudo.*;

//
import static org.krudo.Tool.*;
import static org.krudo.Debug.*;
import static org.krudo.Constant.*;

//
public class Checkmate5 
{
    //
    public static void main(String[] args) 
    {
        PVs.init();
        
        Moves.init();
        
        Captures.init();
                
        String f = "K1k5/P1Pp4/1p1P4/8/p7/P2P4/8/8 w";
        
        Node n = new Node();
        
        n.startpos(f);
                      
        Search s = new Search(n);
        
        
        
        try {
            s.start(11);
        } catch (Exception e)
        {
            dump(n);
            dump(n.L);
            e.printStackTrace();
        }
    }    
}
