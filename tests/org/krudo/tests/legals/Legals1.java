/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.legals;

//
import static org.krudo.Config.*;
import static org.krudo.Tool.*;
import static org.krudo.debug.Debug.*;

//
import org.krudo.*;

//
public class Legals1 
{
    //
    public static void main(String[] args)
    {    
        //
        if (!MOVE_LEGALS) {
            exit("set MOVE_LEGALS = true and retry");
        }
        
        //
        DEBUG_SHOW_MOVE_WEIGHT = true;
        
        //
        Moves.init();
        
        //
        Node n = new Node();

        //
        n.startpos();
        
        //
        dump(n);
        
        //
        n.legals();
        
        //
        dump(n.legals);
    }
}
