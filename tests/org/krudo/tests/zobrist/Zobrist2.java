/**
 * Krudo 0.16a 
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.zobrist;

//
import org.krudo.*;

//
import static org.krudo.util.Debug.*;

// 
public class Zobrist2 
{
    //
    public static void main(String[] args) 
    {
        /**
         * Zobrist progressivo dopo una domove o una unmove
         */
        
        Node n = new Node();
        
        n.startpos();
        
        dump(n); // 463b96181691fc9c
        
        n.domove("e2e4");
        
        dump(n); // 823c9b50fd114196
        
        n.domove("d7d5");
        
        dump(n); // 0756b94461c50fb0
        
        n.domove("e4e5");
        
        dump(n); // 662fafb965db29d4
        
        n.domove("f7f5");
        
        dump(n); // 22a48b5a8e47ff78
        
        n.domove("e1e2");
        
        dump(n); // 652a607ca3f242c1
        
        n.domove("e8f7");
        
        dump(n); // 00fdd303c946bdd9
    }    
}
