/**
 * Krudo 0.16a 
 * by Francesco Bianco <bianco@javanile.org>
 */

// utility to parse fen string
package org.krudo.utils;

// required non-static classes
import org.krudo.Node;

// required static classes and methods
import static org.krudo.Const.*;
import static org.krudo.utils.Trans.*;

// fen class utility
public final class Fen {
		
	// parse fen and apply status into node passed
	public static final void parse(
		final Node n,  // node status position rappresentation apply to
		final String f // fen string with position to parse
	) {
		
		// base status fields
		n.t	= w; // color-side to play
		n.c	= 0b1111; // castling status
		n.e	= 0;
		n.cw = 0;
		n.cb = 0;
		n.wks = e1;
		n.bks = e8;
		n.hm = 0;
		n.n	= 1;
		n.i	= 0;
					
		//
		String[] p = f.split("\\s");
		
		//
		for(int i=0; i<p.length; i++) {
			
			//
			switch(i) {				
				
				// position
				case 0: 		
					int s = a8;		
					for(int j=0; j<p[i].length(); j++) {					
						switch(p[i].charAt(j)) {
							case 'p': n.B[s] = bp; n.cb++; break;
							case 'n': n.B[s] = bn; n.cb++; break;
							case 'b': n.B[s] = bb; n.cb++; break;
							case 'r': n.B[s] = br; n.cb++; break;
							case 'q': n.B[s] = bq; n.cb++; break;
							case 'k': n.B[s] = bk; n.bks = s; n.cb++; break;
							case 'P': n.B[s] = wp; n.cw++; break;
							case 'N': n.B[s] = wn; n.cw++; break;
							case 'B': n.B[s] = wb; n.cw++; break;
							case 'R': n.B[s] = wr; n.cw++; break;
							case 'Q': n.B[s] = wq; n.cw++; break;
							case 'K': n.B[s] = wk; n.wks = s; n.cw++; break;
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
					switch(p[i].charAt(0)) {
						case 'b': n.t = b; break;	
						case 'w': n.t = w; break;
					}
					break;
				
				// castling	
				case 2:
					for(int i1=0; i1<p[i].length(); i1++) {					
						switch(p[i].charAt(i1)) {
							case 'K': n.c ^= wkc; break;	
							case 'Q': n.c ^= wqc; break;
							case 'k': n.c ^= bkc; break;	
							case 'q': n.c ^= bqc; break;
						}
					}
					break;					

				// en-passant	
				case 3:
					if (p[i].charAt(0) != '-') {
						n.e = s2i(""+p[i].charAt(0)+p[i].charAt(1));
					}
					break;					
					
				// half-moves after pawn move or capture	
				case 4:
					n.hm = Integer.parseInt(p[i]);
					break;					
					
				// from the beginning	
				case 5:
					n.n = Integer.parseInt(p[i]);
					break;								
			}			
		}		
	}
}
