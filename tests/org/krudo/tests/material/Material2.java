/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.material;

//
import org.krudo.*;
    
//
import static org.krudo.Tool.*;
import static org.krudo.Debug.*;
    
//
public class Material2 
{
    //
    public static void main(String[] args) 
    {    
        //
        DEBUG_SHOW_MOVE_WEIGHT = true;
        
        //
        Moves.init();

        //
        Node n = new Node();

        //
        n.startpos("k7/7P/8/8/8/8/8/K w");
        
        //
        dump(n);
       
        //
        n.domove("h7h8b");
        
        //
        dump(n);
        
        //
        n.unmove();
        
        //
        dump(n);
    }
}
