/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.book;

//
import static org.krudo.Tool.*;

//
import org.krudo.Book;
import org.krudo.Node;

//
public class Book1 
{
    //
    public static void main(String[] args) 
    {
        //
        long[] nodes = new long[] 
        {
            // starting position
            0x463b96181691fc9cL,

            // position after e2e4
            0x823c9b50fd114196L,

            // position after e2e4 d7d5
            0x0756b94461c50fb0L,

            /*
            position after e2e4 d7d5 e4e5
            FEN=rnbqkbnr/ppp1pppp/8/3pP3/8/8/PPPP1PPP/RNBQKBNR b KQkq - 0 2
            key=662fafb965db29d4

            position after e2e4 d7d5 e4e5 f7f5
            FEN=rnbqkbnr/ppp1p1pp/8/3pPp2/8/8/PPPP1PPP/RNBQKBNR w KQkq f6 0 3
            key=22a48b5a8e47ff78

            position after e2e4 d7d5 e4e5 f7f5 e1e2
            FEN=rnbqkbnr/ppp1p1pp/8/3pPp2/8/8/PPPPKPPP/RNBQ1BNR b kq - 0 3
            key=652a607ca3f242c1

            position after e2e4 d7d5 e4e5 f7f5 e1e2 e8f7
            FEN=rnbq1bnr/ppp1pkpp/8/3pPp2/8/8/PPPPKPPP/RNBQ1BNR w - - 0 4
            key=00fdd303c946bdd9

            position after a2a4 b7b5 h2h4 b5b4 c2c4
            FEN=rnbqkbnr/p1pppppp/8/8/PpP4P/8/1P1PPPP1/RNBQKBNR b KQkq c3 0 3
            key=3c8123ea7b067637

            position after a2a4 b7b5 h2h4 b5b4 c2c4 b4c3 a1a3
            FEN=rnbqkbnr/p1pppppp/8/8/P6P/R1p5/1P1PPPP1/1NBQKBNR b Kkq - 0 4
            key=5c3f9b829b279560*/
        };
        
        Book.open();
    
        for (int i = 0; i < nodes.length; i++) {
            Book.list(nodes[i]);
        }
    
        Book.exit();
    }    
}
