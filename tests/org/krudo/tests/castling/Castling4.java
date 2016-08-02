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
        dump(n);     
    }    
}
