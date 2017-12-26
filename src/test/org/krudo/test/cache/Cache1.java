/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.test.cache;

//
import org.krudo.*;

//
import static org.krudo.Constants.*;

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
        //dump(s);
                
        //
        s.start(1);
        
        //
        //Legals.info();
    }    
}
