/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.attack;

//
import org.krudo.Node;
import org.krudo.Move;

//
import static org.krudo.Constant.*;
import static org.krudo.util.Tool.*;
import static org.krudo.util.Debug.*;

//
public class Attack1 
{
    //
    public static void main(String[] args) 
    {
        //
        DEBUG_SHOW_WEIGTH = true;
        
        //
        Node n = new Node();

        //
        n.startpos();
        
        //
        print(n.white_attack(e4));
    }    
}
