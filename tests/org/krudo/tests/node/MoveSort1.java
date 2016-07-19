/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.node;

//
import org.krudo.*;

//
import static org.krudo.Tool.*;
import static org.krudo.Debug.*;

//
public class MoveSort1 
{
    //
    public static void main(String[] args) 
    {    
        //
        DEBUG_SHOW_MOVE_WEIGHT = true;
        
        //
        Node n = new Node();

        //
        n.startpos();
        
        //
        n.domove("e2e4");
        
        //
        Move m = n.legals();
        
        //
        dump(n, m);
        
        //
        m.sort();
        
        //
        dump(n, m);
        
        //
        print();
    }    
}
