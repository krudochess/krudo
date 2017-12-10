/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.book;

// required non-static class
import org.krudo.Book;
import org.krudo.Node;
import org.krudo.Moves;

// required static class
import static org.krudo.tests.debug.Dump.*;

// 
public class Book2 
{
    //
    public static void main(String[] args) 
    {     
        //
        debug_set_book("bin/krudo.bin");
        
        print(debug_get_book());
        
        //
        Moves.init();
        
        // 
        Node n = new Node();
        
        //
        n.startpos();
        
        //
        String m = Book.rand(n.phk);

        //
        while(m != null) 
        {   
            //
            n.domove(m);

            //
            m = Book.rand(n.phk);
        }
        
        //
        dump(n);    
        
        //
        dump(n.L);            
    }    
}
