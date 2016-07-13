/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.search;

import static org.krudo.util.Debug.*;
import static org.krudo.Constant.*;
import static org.krudo.util.Decode.*;
import static org.krudo.util.Tool.*;

import org.krudo.Engine;
import org.krudo.*;

//
public class Search4 {

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
        Search.walk(n, 10, 1);
        
        //
        Legals.info();
        
        //
        Eval.info();
          
        //
        print(perft(n,5));  
    }
}
