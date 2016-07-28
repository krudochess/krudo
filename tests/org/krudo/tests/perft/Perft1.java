/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.perft;

//
import org.krudo.*;
import org.krudo.Legals;

//
import static org.krudo.Tool.*;
import static org.krudo.Debug.*;
import static org.krudo.Config.*;

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
        4865609    1269 ms
        119060324  19034 ms
        \*/
        
        //
        CACHE_LEGALS = false;
            
        //
        Moves.init();
        
        //
        Node n = new Node();
                       
        //
        n.legals();
        
        //
        dump(n);
        
        //
        dump(n.legals);
        
        //
        try
        {
            //print(info());
                    
            /*_*/
            for (int i = 1; i <= 5; i++) 
            {
                print(perft(n, i)) ;
            }
           
            Legals.info();
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
