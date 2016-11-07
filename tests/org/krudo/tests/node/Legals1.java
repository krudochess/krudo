/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.node;

//
import org.krudo.Node;
import org.krudo.Move;

//
import static org.krudo.Debug.*;

//
public class Legals1 {

    //
    public static void main(String[] args) {
        
        //
        DEBUG_SHOW_MOVE_WEIGHT = true;
        
        //
        DEBUG_SHOW_ALGEBRIC = true;
        
        //
        Node n = new Node();

        //
        n.startpos();
        
        //
        n.domove("e2e4");
        
        //
        //Move m = n.legals();
        
        //
        //dump(m, n);            
    }    
}
