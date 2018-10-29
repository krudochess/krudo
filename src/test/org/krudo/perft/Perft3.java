/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.perft;

//
import org.krudo.Node;

//
import static org.krudo.Tool.*;
import static org.krudo.test.debug.Perft.*;

//
public class Perft3 
{
    //
    public static void main(String[] args) 
    {        
        /*\
        FEN: 8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - -
        14            0 ms
        191            0 ms
        2812        0 ms
        43238        0 ms
        193690690    0 ms        
        \*/
                
        //
        Node n = new Node("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - -");

        //
        int d = 2;
        
        //
        try {
            for (int i = 1; i <= d; i++) 
            {
                print(perft(n, i));
            }
            //print(perft_table(n, d));
        } catch (Exception e) {
            //dump(n);
            //dump(n.L);
            //dump(e);
        }            
    }    
}
