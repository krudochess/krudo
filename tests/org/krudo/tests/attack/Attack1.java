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
import static org.krudo.Tool.*;
import static org.krudo.Debug.*;

//
public class Attack1 
{
    //
    public static void main(String[] args) 
    {
        //
        DEBUG_SHOW_MOVE_WEIGHT = true;
        
        //
        Node n = new Node();

        //
        n.startpos();
        
        //
        print(n.white_attack(e4));
    }    
}
