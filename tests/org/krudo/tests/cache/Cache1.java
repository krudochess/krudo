/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.cache;

//
import org.krudo.*;

//
import static org.krudo.Tool.*;
import static org.krudo.Debug.*;

// 
public class Cache1 
{
    //
    public static void main(String[] args) 
    {
        //
        Node n = new Node();
        
        //
        Search s = new Search(n);
        
        //
        s.start(1);
        
        //
        Legals.info();                
    }    
}
