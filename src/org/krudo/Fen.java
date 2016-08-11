/**
 * Krudo 0.16a 
 * by Francesco Bianco <bianco@javanile.org>
 */

// utility to parse fen string
package org.krudo;

// required static classes and methods
import static org.krudo.Constant.*;

// fen class utility
public final class Fen 
{		
	// parse fen and apply status into node passed
	public static final void parse(
		final Node node,  // node status position rappresentation apply to
		final String fen  // fen string with position to parse
	) {
		// base status fields
		node.t = w;      // color-side to play
		node.c = 0x1111; // castling status all castling disabled (negative logic)
		node.e = xx;
		node.cw = 0;
		node.cb = 0;
        node.ote = 256;  // no piece on board ending game status is 256
		node.wks = xx;
		node.bks = xx;
		node.hm = 0;
		node.n = 0;
        
        //
        node.L.i = 0;
        
        // clear board matirial 
        for (int p = 0; p < 12; p++) { node.M[p] = 0; }
		        
        // clear board position
        for (int s = 0; s < 64; s++) { node.B[s] = O; }
					
		//
		String[] portion = fen.split("\\s");
		
		//
		for (int section = 0; section < portion.length; section++) 
        {	
			//
			switch (section) 
            {					
				// position
				case 0: 		
					int s = a8;		
					for (int i=0; i<portion[section].length(); i++) 
                    {					
						switch(portion[section].charAt(i)) 
                        {
							case 'p': node.B[s] = bp; node.M[bp&lo]++; node.cb++; break;
							case 'n': node.B[s] = bn; node.M[bn&lo]++; node.cb++; node.ote -= Eval.OTEW[bn & lo]; break;
							case 'b': node.B[s] = bb; node.M[bb&lo]++; node.cb++; node.ote -= Eval.OTEW[bb & lo]; break;
							case 'r': node.B[s] = br; node.M[br&lo]++; node.cb++; node.ote -= Eval.OTEW[br & lo]; break;
							case 'q': node.B[s] = bq; node.M[bq&lo]++; node.cb++; node.ote -= Eval.OTEW[bq & lo]; break;
							case 'k': node.B[s] = bk; node.M[bk&lo]++; node.bks = s; node.cb++; break;
							case 'P': node.B[s] = wp; node.M[wp&lo]++; node.cw++; break;
							case 'N': node.B[s] = wn; node.M[wn&lo]++; node.cw++; node.ote -= Eval.OTEW[wn & lo]; break;
							case 'B': node.B[s] = wb; node.M[wb&lo]++; node.cw++; node.ote -= Eval.OTEW[wb & lo]; break;
							case 'R': node.B[s] = wr; node.M[wr&lo]++; node.cw++; node.ote -= Eval.OTEW[wr & lo]; break;
							case 'Q': node.B[s] = wq; node.M[wq&lo]++; node.cw++; node.ote -= Eval.OTEW[wq & lo]; break;
							case 'K': node.B[s] = wk; node.M[wk&lo]++; node.wks = s; node.cw++; break;
							case '/': s = s - 17; break;						
							case '1': s = s + 0; break;
							case '2': s = s + 1; break;
							case '3': s = s + 2; break;
							case '4': s = s + 3; break;
							case '5': s = s + 4; break;
							case '6': s = s + 5; break;
							case '7': s = s + 6; break;
							case '8': s = s + 7; break;								
						}
						s++;
					}
					break;
				
				// turn
				case 1:	
					switch(portion[section].charAt(0)) 
                    {
						case 'b': node.t = b; break;	
						case 'w': node.t = w; break;
					}
					break;
				
				// castling	
				case 2:
					for (int i=0; i<portion[section].length(); i++) 
                    {					
						switch (portion[section].charAt(i)) 
                        {
							case 'K': node.c ^= K___; break;	
							case 'Q': node.c ^= _Q__; break;
							case 'k': node.c ^= __k_; break;	
							case 'q': node.c ^= ___q; break;
						}
					}
					break;					

				// en-passant	
				case 3:
					if (portion[section].charAt(0) != '-') 
                    {
						node.e = Parse.parse_square(""+portion[section].charAt(0)+portion[section].charAt(1));
					}
					break;					
					
				// half-moves after pawn move or capture	
				case 4:
					node.hm = Integer.parseInt(portion[section]);
					break;					
					
				// from the beginning	
				case 5:
					node.n = Integer.parseInt(portion[section]);
					break;								
			}			
		}		
	}
}
