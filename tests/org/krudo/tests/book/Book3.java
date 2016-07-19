/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.book;

import java.util.ArrayList;

//
import static org.krudo.Debug.*;
import static org.krudo.Tool.*;

//
import org.krudo.Book;
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
        ArrayList<String> m = Book.list(n.h);

        //
        dump(n);
        
        // print out position
        print(m);
    }    
}
