/**
 * Krudo 0.16a 
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.zobrist;

//
import org.krudo.*;

//
import static org.krudo.debug.Debug.*;
import static org.krudo.Tool.echo;
import static org.krudo.Tool.hex;

// 
public class Zobrist3 
{
    //
    public static void main(String[] args) 
    {
        /**
         * Zobrist progrssivo dopo una domove o una unmove
         */
        
        //
        Node n = new Node();
        
        //
        String h;
        
        //
        n.startpos();
                
        //
        n.domove("a2a4 b7b5 h2h4 b5b4 c2c4".split("\\s"));        
        h = "3c8123ea7b067637";
        echo(hex(n.phk),"==",h,"->",h.equals(hex(n.phk))+"\n");
        
        //
        n.domove("b4c3 a1a3".split("\\s"));        
        h = "5c3f9b829b279560";
        echo(hex(n.phk),"==",h,"->",h.equals(hex(n.phk))+"\n");        
    }    
}
