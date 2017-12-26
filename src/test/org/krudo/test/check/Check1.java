package org.krudo.test.check;

//
import org.krudo.Moves;

// 
import org.krudo.Node;

// 
public class Check1 {

    //
    public static void main(String[] args)
    {
        //
        Moves.init();

        // 
        Node n = new Node();

        //
        n.startpos("4k3/R7/8/8/8/8/1n6/3K4 w");

        //
        try
        {            
            // print out position
            //dump(n);
            
            //
            n.legals();

            //
            //dump(n.legals);
        } 
        
        //
        catch (Exception e) 
        {
            //dump(n);
            e.printStackTrace();
        }
    }    
}
