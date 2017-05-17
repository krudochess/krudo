/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.perft;

//
import org.krudo.*;
import org.krudo.Legals;

//
import static org.krudo.Tool.*;
import static org.krudo.debug.Debug.*;
import static org.krudo.Config.*;

//
public class Perft4 
{
    //
    public static void main(String[] args) 
    {    
        // 4865609     3817 ms   1274 kNPS
        // 4865609     3834 ms   1269 kNPS
        // 4865609     4404 ms   1104 kNPS
        // 4865609     5421 ms    897 kNPS
        // 4865609     6547 ms    743 kNPS
        
        CACHE_LEGALS  = true;
        MOVE_LEGALS   = true;
        REMAPS_PSEUDO = true;
        
        Krudo.init();
        
        Node n = new Node();
                     
        n.startpos();
        
        print(perft(n, 5));
    }    
}
