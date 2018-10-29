/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

// 
package org.krudo.checkmate;

//
import org.krudo.*;

//


//
public class Checkmate3 
{
    //
    public static void main(String[] args) 
    {
        PVs.init();
        
        Moves.init();
        
        Captures.init();
                
        String fen = "b7/PP6/8/8/7K/6B1/6N1/4R1bk w";
                                      
        Search search = new Search(fen);
                       
        try {
            search.start(5);
        } catch (Exception e)
        {
            //dump(search.node);
            //dump(search.node.L);
            e.printStackTrace();
        }
    }    
}
