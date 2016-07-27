/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.analize;

import org.krudo.Legals;
import static org.krudo.Tool.*;
import static org.krudo.Debug.*;

//
import org.krudo.Node;
import org.krudo.Move;
import org.krudo.Moves;
import org.krudo.Search;

// 
public class Analize1 
{
    //
    public static void main(String[] args) 
    {
        //
        DEBUG_SHOW_MOVE_WEIGHT = true;
        
        //
        Moves.init();
        
        // create a node to service the search
        Node n = new Node();

        //
        String fen = readfile("./console/analize.fen");
                
        //
        n.startpos(fen);
        
        //
        dump(n);
        
        //
        n.legals();
        
        //
        dump(n.legals.sort());
                
        // create a serach engine based-on the node
        Search s = new Search(n);
        
        //
        s.start(20, 100000);                
    }    
}
