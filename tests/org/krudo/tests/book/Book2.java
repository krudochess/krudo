/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.book;

// required non-static class
import org.krudo.Node;
import org.krudo.Book;

// required static class
import static org.krudo.Tool.*;
import static org.krudo.Debug.*;
import org.krudo.Moves;

// 
public class Book2 
{
    //
    public static void main(String[] args) 
    {       
        //
        Moves.init();
        
        // 
        Node n = new Node();
        
        //
        n.startpos();
        
        //
        String m = Book.rand(n.h);

        //
        while(m != null) 
        {   
            //
            n.domove(m);

            //
            m = Book.rand(n.h);
        }
        
        //
        dump(n);    
        
        //
        dump(n.L);            
    }    
}
