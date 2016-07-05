/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.node;

//
import org.krudo.Node;

//
import static org.krudo.util.Debug.*;

//
public class Node1 
{
    //
    public static void main(String[] args) 
    {
        //
        Node n = new Node();
        
        //
        n.startpos();
         
        //
        n.domove("e2e4");

        //
        dump(n);
        
        //
        n.unmove();
        
        //
        dump(n);        
    }    
}
