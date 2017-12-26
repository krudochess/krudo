/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.test.book;

import java.util.ArrayList;

//
import static org.krudo.test.debug.Debug.*;
import static org.krudo.Tool.*;

//
import org.krudo.Book;
import org.krudo.Node;

// 
public class Book3 
{
    //
    public static void main(String[] args) 
    {
        //
        //debug_set_book("bin/krudo.bin");
        
        //print(debug_get_book());
                
        // 
        Node n = new Node();
        
        //
        ArrayList<String> m = Book.list(n.phk);

        //
        //dump(n);
        
        // print out position
        print(m);
    }    
}
