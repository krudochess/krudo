/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.test.bench;

import static org.krudo.test.debug.Debug.*;

import static org.krudo.Tool.*;

//
public class Bench4 {

    //
    public static void main(String[] args) {
//
        long t = time();
        
slower(100);        
        
        
        //
        print(time() - t, "ms");
        
    }
    
}
