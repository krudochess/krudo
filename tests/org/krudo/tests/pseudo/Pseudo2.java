/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.pseudo;

//
import static org.krudo.Config.MOVE_LEGALS;
import org.krudo.Node;

//
import static org.krudo.Tool.*;
import static org.krudo.debug.Debug.*;

//
public class Pseudo2 
{
    //
    public static void main(String[] args)
    {
        //
        if (MOVE_LEGALS) {
            exit("set int Config class MOVE_LEGALS = false and retry");
        }
        
        //
        Node n = new Node();

        //
        n.startpos();
        
        //
        dump(n);
        
        //
        //dump(n.legals());
    }    
}
