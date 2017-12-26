/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.test.material;

//
import org.krudo.*;
    
//
import static org.krudo.Tool.*;
import static org.krudo.test.debug.Debug.*;
    
//
public class Material1 
{
    //
    public static void main(String[] args) 
    {    
        //
        DEBUG_SHOW_MOVE_WEIGHT = true;
        
        //
        Moves.init();

        //
        Node n = new Node();

        //
        n.startpos();
        
        //
        //dump(n);
        
        //
        //dump(n.M);
        
        n.domove("e2e4");
        
        //
        //dump(n);
        
        //
        //dump(n.M);
        
        n.domove("d7d5");
        
        //
        //dump(n);
        
        //
        //dump(n.M);
        
        n.domove("e4d5");
        
        //
        //dump(n);
        
        //
        //dump(n.M);
        
        n.domove("d8d5");
        
        //
        //dump(n);
        
        //
        //dump(n.M);
        
        n.unmove();
        
        //
        //dump(n);
        
        //
        //dump(n.M);
        
        n.unmove();
        
        //
        //dump(n);
        
        //
        //dump(n.M);
        
        n.unmove();
        
        //
        //dump(n);
        
        //
        //dump(n.M);
        
        //
        print("eval: "+Eval.node(n));
    }
}
