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
import static org.krudo.tests.debug.Debug.*;
import static org.krudo.Constants.*;

//
public class Checkmate4 
{
    //
    public static void main(String[] args) 
    {
        PVs.init();
        
        Moves.init();
        
        Captures.init();
                
        String f = "8/8/8/8/7k/8/PPPPPPPP/RNBQKBNR w KQkq";
        
                      
        Search s = new Search(f);
                      
        try
        {
            s.start(5);
        } 
        
        catch (Exception e)
        {
            dump(s.node);
            dump(s.node.L);
            e.printStackTrace();
        }
    }    
}
