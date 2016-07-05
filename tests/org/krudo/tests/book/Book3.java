/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.book;

//
import static org.krudo.util.Debug.*;

//
import org.krudo.util.Book;
import org.krudo.Move;
import org.krudo.Node;

// 
public class Book3 
{
    //
    public static void main(String[] args) 
    {
        // 
        Node n = new Node();
        
        //
        Move m = Book.list(n.h);

        //
        dump(n);
        
        // print out position
        dump(m);
    }    
}
