/**
 * Krudo 0.18a - Java chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.debug;

//

import static org.krudo.Config.MOVE_TWIN;
import static org.krudo.Constants.ECAP;
import static org.krudo.Constants.O;
import org.krudo.Move;
import org.krudo.Moves;
import org.krudo.Node;
import static org.krudo.Tool.rpad;
import static org.krudo.tests.debug.Debug.count_captures;
import static org.krudo.tests.debug.Debug.count_enpassant;

public class Perft 
{
    //
	public final static String perft(
        final Node n,
        final int d
    ) {
        //
		long s = System.currentTimeMillis();
		
        //
        long c = perft_quick_iteration(n, d);
		
        //
        long e = System.currentTimeMillis();
		
        //
        long m = (e - s);
		
        //
        long r = m > 0 ? c / m : 0;
		
        //
        return "perft("+d+"): "
             + rpad(c,10)
             + rpad(String.valueOf(m)+" ms",12)
             + rpad(r+" kNPS",12);
	}
	
    //
	public final static long perft_quick_iteration(
        final Node n, 
        final int d
    ) {		        
        //
		if (d == 0) { return 1; }
                
        //
        int c = 0;
        
        //
        n.legals();
                            
        //
        Move m = n.legals.twin();
                        
        //
        final int l = m.count;
        
        //
        for (int i = 0; i != l; i++) 
        {	
            //
            n.domove(m, i);		

            //
            c += perft_quick_iteration(n, d - 1);
            
            //
            n.unmove();
        } 

        // 
        if (MOVE_TWIN) { Moves.free(m); }
        
        //
        return c;
	}  
    
	//
	public final static long doing(Node n, int d) 
    {		        
        //
		if (d == 0) { 
            //n.captures();
            return 1; 
        }
                
        //
        int c = 0;
        
        //
        n.legals();
                            
        //
        Move m = n.legals.sort().twin();
                
        /*
        //
        if (m.i == 0) {
            print("mate: "+desc(n.L));   
            if (n.L.s[0] == g2) {                
                dump(n);
                dump(n.L);
                print(n.L.i);
                n.unmove();
                dump(n);
                dump(n.L);
                print(n.L.i);
                exit();
            }
        }
        */
        
        //
        final int l = m.count;
        
        //
        for (int i = 0; i != l; i++) 
        {	
            //
            if (m.k[i] == ECAP) { count_enpassant++; }

            //
            if (n.B[m.v[i]] != O) { count_captures++; }                        
                        
            //
            n.domove(m, i);		
            
            //
            //if () { count_incheck++; }
            
            //
            c += doing(n, d-1);
            
            //
            n.unmove();
        } 

        // 
        if (MOVE_TWIN) { Moves.free(m); }
        
        //
        return c;
	}  
}
