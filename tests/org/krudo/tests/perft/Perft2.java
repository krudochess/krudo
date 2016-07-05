/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.benchmark;

//
import static org.krudo.util.Debug.*;
import static org.krudo.util.Tool.*;

//
import org.krudo.Node;

//
public class Perft2 {

    //
    public static void main(String[] args) {
        
        /*\
        FEN: r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -
        48            0 ms
        2039        0 ms
        97862        0 ms
        4085603        0 ms
        193690690    0 ms        
        \*/
                
        //Node n = new Node("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -");
        Node n = new Node();
        
        n.startpos("3k4/8/8/8/8/8/8/4K3 w KQkq -");
                
        try {
            /*_*/
            for(int i=1; i<=10; i++) {
                echo(perft(n,i));
            }
            /*/
            Perft.table(n,5);
            /*_*/
        } catch (Exception e) {
            dump(n);
            dump(n.L);
            dump(e);
        }            
    }    
}
