/**
 * Krudo 0.16a 
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.pseudo;

//
import org.krudo.*;

//
import static org.krudo.Config.*;
import static org.krudo.Tool.*;

//
public class Pseudo1 
{
    //
    public static void main(String[] args) 
    {    
        //
        if (MOVE_LEGALS) {
            exit("set MOVE_LEGALS = false and retry");
        }
        
        //
        Node n = new Node();

        //
        n.startpos();
        
        //
        //dump(n);
        
        //
        //dump(n.legals());
    }
}
