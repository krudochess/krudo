/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.attack;

//
import org.krudo.*;

//
import static org.krudo.Tool.*;
import static org.krudo.Debug.*;
import static org.krudo.Constant.*;

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
        n.domove("d2d3");
        
        //
        print(n.white_attack(e4));
    }    
}
