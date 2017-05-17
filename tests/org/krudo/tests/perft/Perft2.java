/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.perft;

//
import org.krudo.*;

//
import static org.krudo.tests.debug.Debug.*;
import static org.krudo.Tool.*;

//
public class Perft2 
{
    //
    public static void main(String[] args) 
    {    
        /*\
        FEN: r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -
        48         0 ms
        2039       0 ms
        97862      0 ms
        4085603    0 ms
        193690690  0 ms        
        \*/
        
        Moves.init();
        
        Node n = new Node();
        
        n.startpos("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -");
             
        //dump(n);
        
        //dump(n.legals());
        
        try 
        {
            /*_*/
            for (int i = 1; i <= 2; i++)
            {
                print(perft(n,i));
            }
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
