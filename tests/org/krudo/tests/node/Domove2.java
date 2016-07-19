/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.node;

//
import org.krudo.Node;

//
import static org.krudo.Debug.*;

//
public class Domove2 {

    //
    public static void main(String[] args) {

        //
        Node n = new Node();
        
        //
        n.startpos();
         
        //
        String[] moves = new String[] {
            "e2e4","b8c6","e4e5","f7f5","e5f6"
        }; 
        
        //
        n.domove(moves);

        //
        dump(n);
        
        //
        n.unmove(moves.length);
        
        //
        dump(n);        
    }    
}
