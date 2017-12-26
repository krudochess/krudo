/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.test.castling;

//
import org.krudo.Node;

//
import static org.krudo.Constants.*;
import static org.krudo.Tool.*;

import org.krudo.Moves;

// 
public class Castling3 
{
    //
    public static void main(String[] args) 
    {
        //
        Moves.init();
 
        //
        Node n = new Node();
        
        //
        n.startpos();
        
        //
        n.domove("d2d4 c7c6 e2e3 b8a6 g1f3 d8a5 c1d2 a5b6 d1c1 d7d5 f1a6 b6a6 b1c3 c8f5".split("\\s"));
             
        //
        print(n.black_attack(f1));
        
        //
        //dump(n);
        
        //
        n.legals();
        
        //
        //dump(n.legals);
    }    
}
