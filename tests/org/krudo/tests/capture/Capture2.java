package org.krudo.tests.capture;

//
import org.krudo.Book;
import org.krudo.Moves;
import static org.krudo.Debug.*;
import static org.krudo.Tool.*;

// 
import org.krudo.Node;

// 
public class Capture2 
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
        Book.walk(n, 0);
        
        //
        dump(n);
        
        //
        dump(n.capture());
    }    
}
