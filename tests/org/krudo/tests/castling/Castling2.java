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
import static org.krudo.Tool.*;
import static org.krudo.tests.debug.Debug.*;

// 
public class Castling2 
{
    //
    public static void main(String[] args) 
    {
        //
        String p = "8/8/8/8/8/3n4/8/4K2R w KQkq";
 
        //
        Node n = new Node();
        
        //
        n.startpos(p);
        
        //
        dump(n);
        
        //
        n.legals();
        
        //
        dump(n.legals);        
    }    
}
