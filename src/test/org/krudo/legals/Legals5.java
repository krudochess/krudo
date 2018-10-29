/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.legals;

//
import org.krudo.*;

//
import static org.krudo.test.debug.Debug.*;

//
public class Legals5 
{
    //
    public static void main(String[] args) 
    {    
        //
        DEBUG_SHOW_MOVE_WEIGHT = false;
        
        //
        Krudo.init();

        //
        Node n = new Node();

        //
        n.startpos("2rr3k/pp3pp1/1nnqbN1p/3pN3/2pP4/2P3Q1/PPB4P/R4RK1 w");
        
        //
        //dump(n);
        
        //
        n.legals();
                       
        //
        //dump(n.legals);
    }
}
