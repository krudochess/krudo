package org.krudo.test.castling;

//
import org.krudo.Moves;

// 
import org.krudo.Node;

// 
public class Castling1 {

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
            n.domove("b1a3 g8f6 g1f3".split("\\s"));

            //n.domove(n.legals(), 14);
            
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
