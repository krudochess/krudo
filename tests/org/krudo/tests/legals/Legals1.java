/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.legals;

//
import static org.krudo.Constant.*;
import static org.krudo.util.Debug.*;
import static org.krudo.util.Tool.*;
import static org.krudo.util.Decode.*;

//
import org.krudo.*;

//
public class Legals1 
{
    //
    public static void main(String[] args)
    {    
        //
        //if (!MOVE_LEGALS) {
          //  exit("set MOVE_LEGALS = true and retry");
        //}
        
        //
        Node n = new Node();

        //
        n.startpos();
        
        //
        print(n);
        
        //
        print(n.legals());
    }
}
