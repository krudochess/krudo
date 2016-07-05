/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.move;

//
import static org.krudo.util.Debug.*;
import static org.krudo.util.Tool.*;

// 
import org.krudo.Node;
import org.krudo.Move;

// 
public class Move1 {

    //
    public static void main(String[] args) {
        
        Move l = null/*Move.pop()*/; 
        
        long t = System.currentTimeMillis();
        
        for(int i=0; i<50000; i++) {
            
            Move m = null/*Move.pop()*/; 
        }

        echo (System.currentTimeMillis()-t);

t = System.currentTimeMillis();

        for(int i=0; i<50000; i++) {
            
            Move m = /*Move.pop()*/null; 
        }

        echo (System.currentTimeMillis()-t);
    }    
}
