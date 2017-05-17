package org.krudo.tests.capture;

//
import org.krudo.Moves;
import static org.krudo.tests.debug.Debug.*;
import static org.krudo.Tool.*;

// 
import org.krudo.Node;

// 
public class Capture1 
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
        try
        {
            // do move e2e4
            n.domove("e2e4 b8a6".split("\\s"));

            //
            n.legals();
            
            //
            n.domove(n.legals, 14);
            
            // print out position
            dump(n);   

            //
            n.legals();
            
            //
            dump(n.legals);  
        } 
        
        catch (Exception e) 
        {
            //
            dump(n);
            
            //
            e.printStackTrace();
        }
    }    
}
