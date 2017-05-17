/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.pawn;

//
import org.krudo.*;

//
import static org.krudo.Tool.*;
import static org.krudo.tests.debug.Debug.*;

//
public class Pawn2 {

    //
    public static void main(String[] args)
    {
        int wnp = 2;
        int bnp = 2;
        int wps = 0;
        int bps = 0;
          
        while (Integer.bitCount(wps) != wnp) {
            wps = rand(0,255);
        }
        
        while (Integer.bitCount(bps) != bnp) {
            bps = rand(0,255);
        }
       
        
        print(Eval.pawn_structure(wps,bps));
          
    }    
}
