/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.book;

// required non-static class
import org.krudo.Node;
import org.krudo.util.Book;

// required static class
import static org.krudo.util.Tool.*;
import static org.krudo.util.Debug.*;

// 
public class Book2 
{
    //
    public static void main(String[] args) 
    {       
        // 
        Node n = new Node();
        
        //
        //n.domove("f2f4");
        
        //
        String m = Book.rand(n);

        //
        while(m != null) 
        {   
            //
            n.domove(m);

            //
            m = Book.rand(n);
        }
        
        //
        dump(n);    
        
        //
        dump(n.L);            
    }    
}
