/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.node;

//
import org.krudo.*;

//


//
public class Node2
{
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
        n.domove("e2e4 a7a5 d2d4 a5a4 g1f3".split("\\s"));

        //
        //dump(n);
        
        //
        //dump(n.legals());        
    }    
}
