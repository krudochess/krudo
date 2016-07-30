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
public class Checkmate2 
{
    //
    public static void main(String[] args) 
    {
        PVs.init();
        
        Moves.init();
        
        Captures.init();
                
        String f = "5nrk/6pp/3N2pp/8/8/8/8/7K b";
        
        Node n = new Node();
        
        n.startpos(f);
                      
        Search s = new Search(n);
        
        dump(n);
        
        s.start(2);
        
    }    
}
