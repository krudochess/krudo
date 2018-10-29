/**
 * Krudo 0.18a - Java chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.perft;

//
import org.krudo.*;

//
import static org.krudo.Tool.*;
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

        /*
        //
        CACHE_LEGALS = true;
        
        //
        MOVE_LEGALS = true;
        
        //
        REMAPS_PSEUDO = true;
        
        //
        Krudo.init();
        
        //
        Node n = new Node();
                     
        //
        n.startpos();
        
        //
        try
        {
            //print(info());
                    
//            /*_*/
//            for (int i = 1; i <= 6; i++)
//            {
//                count_incheck = 0;
//                count_captures = 0;
//                count_enpassant = 0;
//                print(perft(n, i));
//                //print(count_captures, count_enpassant, count_incheck);
//            }
//
//            //
//            dump(n);
//
//            //
//            debug_legals_info();
//            //dump(n);
//            /*/
//            Perft.table(n,5);
//            /*_*/
      /*
        }
        
        //
        catch (Exception e) 
        {
            dump(n);
            dump(n.L);
            dump(e);
        }
        */
    }    
}
