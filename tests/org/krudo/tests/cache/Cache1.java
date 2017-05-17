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
import static org.krudo.debug.Debug.*;
import static org.krudo.Constant.*;

// 
public class Cache1 
{
    //
    public static void main(String[] args) 
    {
        //
        Krudo.init();
        
        //
        Search s = new Search(STARTPOS);
        
        //
        dump(s);
                
        //
        s.start(1);
        
        //
        Legals.info();                
    }    
}
