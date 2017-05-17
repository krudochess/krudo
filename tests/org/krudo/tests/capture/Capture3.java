package org.krudo.tests.capture;

//
import org.krudo.Moves;
import static org.krudo.debug.Debug.*;
import static org.krudo.Tool.*;

// 
import org.krudo.Node;

// 
public class Capture3 
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
            n.domove("d2d4 e7e5 d4e5".split("\\s"));

            //
            n.legals();
            
            //
            //n.domove(n.legals, 14);
            
            n.unmove();
            
            // print out position
            dump(n);   

            //
            //n.legals();
            
            //
            //dump(n.legals);  
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
