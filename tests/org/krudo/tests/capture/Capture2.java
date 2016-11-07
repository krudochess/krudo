package org.krudo.tests.capture;

//
import org.krudo.*;
import static org.krudo.Debug.*;
import static org.krudo.Tool.*;

// 
public class Capture2 
{
    //
    public static void main(String[] args)
    {
        //
        //Krudo.init();

        // 
        Node n = new Node();

        //
        n.startpos();

        //
        Book.walk(n, 0);
        
        //
        dump(n);
        
        //
        n.captures();
        
        //
        dump(n.captures);
    }    
}
