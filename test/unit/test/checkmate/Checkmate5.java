/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

// 
package org.krudo.test.checkmate;

//
import org.krudo.*;

//


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
                              
        Search s = new Search(f);
                       
        try {
            s.start(11);
        } catch (Exception e)
        {
            //dump(s.node);
            //dump(s.node.L);
            e.printStackTrace();
        }
    }    
}
