/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.castling;

//
import org.krudo.Node;

import org.krudo.Search;

//
import static org.krudo.Constant.*;
import static org.krudo.Tool.*;
import static org.krudo.Debug.*;
import org.krudo.Moves;

// 
public class Castling4 
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
        n.domove(split("e2e4 d7d5 e4e5 c8f5 g2g4 f5c8 d2d4 f7f5 g4f5 c8f5 c1g5 b8c6 f1g2 c6b4 b1a3 c7c5 d4c5 d8a5 d1h5 g7g6 h5f3 f5e4 f3h3 b4c2"));
        
        //
        dump(n);
        
        //
        n.legals();
        
        //
        dump(n.legals);
    }    
}
