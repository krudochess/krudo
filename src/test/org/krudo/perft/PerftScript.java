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


//
public class PerftScript 
{
    //
    public static void main(String[] args) 
    {           
/*        //
        Krudo.init();
        
        //
        Node n = new Node();
                     
        //
        n.startpos();
        
        //
        try
        {
            //print(info());
                    
            *//*_*//*
            for (int i = 1; i <= 2; i++) 
            {
                count_incheck = 0;
                count_captures = 0;
                count_enpassant = 0;
                print(perft(n, i));
                //print(count_captures, count_enpassant, count_incheck);
            }
           
            //
            dump(n);
            
            //
            debug_legals_info();
            //dump(n);
            *//*//*
            Perft.table(n,5);
            /*_*//*
        } 
        
        //
        catch (Exception e) 
        {
            dump(n);
            dump(n.L);
            dump(e);
        }        */
    }    
}
