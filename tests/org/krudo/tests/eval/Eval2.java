/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.eval;

//
import static org.krudo.Constant.*;
import static org.krudo.util.Debug.*;
import static org.krudo.util.Tool.*;
import static org.krudo.util.Decode.*;

//
import org.krudo.*;

//
public class Eval2 
{
    
    
	//
	public static void main(String[] args) 
    {

       Moves.init();
           
           
       Node n = new Node();    
        
       n.startpos();
       
       n.legals();
       
       n.domove("e2e4");
       
       print(Eval.node(n));
       
       //Eval.dump();
     
	}
}

