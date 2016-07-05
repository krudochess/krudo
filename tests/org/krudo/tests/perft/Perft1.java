/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.perft;

//
import org.krudo.*;

//
import static org.krudo.util.Tool.*;
import static org.krudo.util.Debug.*;

//
public class Perft1 
{
    //
    public static void main(String[] args) 
    {   
        /*\
        FEN:       STARTPOS
        20         0 ms
        400        5 ms
        8902       26 ms
        197281     160 ms
        4865609    1772 ms
        119060324  38204 ms
        \*/
    
        //
        Moves.init();
        
        //
        Node n = new Node();
        
        //
        n.startpos();
        
        //
        try
        {
            /*_*/
            for (int i = 1; i <= 6; i++) 
            {
                print(perft(n, i)) ;
            }
            //echo (Cache.legals.size());
            //dump(n);
            /*/
            Perft.table(n,5);
            /*_*/
        } 
        
        //
        catch (Exception e) 
        {
            dump(n);
            dump(n.L);
            dump(e);
        }            
    }    
}
