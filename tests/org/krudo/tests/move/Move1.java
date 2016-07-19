/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.move;

// 
import org.krudo.Node;
import org.krudo.Move;

//
import static org.krudo.Tool.*;
import static org.krudo.Debug.*;

// 
public class Move1 {

    //
    public static void main(String[] args) 
    {
        
        Move l = null/*Move.pop()*/; 
        
        long t = System.currentTimeMillis();
        
        for(int i=0; i<50000; i++) {
            
            Move m = null/*Move.pop()*/; 
        }

        echo (System.currentTimeMillis() - t);

t = System.currentTimeMillis();

        for(int i=0; i<50000; i++) 
        {
            
            Move m = /*Move.pop()*/null; 
        }

        echo (System.currentTimeMillis()-t);
    }    
}
