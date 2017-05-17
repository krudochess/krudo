/**
 * Krudo 0.16a 
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.zobrist;

//
import org.krudo.*;

//
import static org.krudo.tests.debug.Debug.*;
import static org.krudo.Tool.echo;
import static org.krudo.Tool.hex;

// 
public class Zobrist2 
{
    //
    public static void main(String[] args) 
    {
        /**
         * Zobrist progressivo dopo una domove o una unmove
         */
        
        Node n = new Node();
        
        String h;
        
        //
        n.startpos();                
        h = "463b96181691fc9c";      
        echo(hex(n.phk),"==",h,"->",h.equals(hex(n.phk))+"\n");
          
        //
        n.domove("e2e4");        
        h = "823c9b50fd114196";        
        echo(hex(n.phk),"==",h,"->",h.equals(hex(n.phk))+"\n");
        
        //
        n.domove("d7d5");        
        h = "0756b94461c50fb0";        
        echo(hex(n.phk),"==",h,"->",h.equals(hex(n.phk))+"\n");
        
        //
        n.domove("e4e5");        
        h = "662fafb965db29d4";
        echo(hex(n.phk),"==",h,"->",h.equals(hex(n.phk))+"\n");
                
        //        
        n.domove("f7f5");        
        h = "22a48b5a8e47ff78";
        echo(hex(n.phk),"==",h,"->",h.equals(hex(n.phk))+"\n");
        
        //
        n.domove("e1e2");        
        h = "652a607ca3f242c1";
        echo(hex(n.phk),"==",h,"->",h.equals(hex(n.phk))+"\n");
        
        //
        n.domove("e8f7");        
        h = "00fdd303c946bdd9";
        echo(hex(n.phk),"==",h,"->",h.equals(hex(n.phk))+"\n");
    }    
}
