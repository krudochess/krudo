/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

// 
package org.krudo.test.checkmate;

//
import org.krudo.*;

//


//
public class Checkmate2 
{
    //
    public static void main(String[] args) 
    {
        //
        PVs.init();
        
        //
        Moves.init();
        
        //
        Captures.init();
          
        //
        String fen = "5nrk/5qpp/3N2pp/8/8/8/8/7K w";
          
        //
        Search search = new Search(fen);
        
        //
        //dump(search);
        
        //
        search.start(1);        
    }    
}
