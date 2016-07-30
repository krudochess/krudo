/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

// 
package org.krudo.tests.checkmate;

//
import org.krudo.*;

//
import static org.krudo.Debug.*;
import static org.krudo.Constant.*;

//
public class Checkmate3 
{
    //
    public static void main(String[] args) 
    {
        PVs.init();
        
        Moves.init();
        
        Captures.init();
                
        String f = "r5k1/7p/8/7Q/8/6PP/3P1bPP/7K w";
        
        Node n = new Node();
        
        n.startpos(f);
                      
        Search s = new Search(n);
        
        dump(n);
        
        s.start(3);
        
    }    
}
