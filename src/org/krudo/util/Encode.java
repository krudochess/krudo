/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.util;

//
import static org.krudo.Constant.*;

//
public final class Encode {

	// row and column to integer
	public static final int cr2i(
		final int c, // col integer
		final int r  // row integer
	) {		
		//
		return r * 8 + c;
	}
	
	// row and column to integer
	public static final int cr2i(
		final char c, // col letter
		final char r  // row number  
	) {
		//
		return (int) (r - '1') * 8 + c - 'a';
	}
	
	// king-of-move to integer
	public static final int k2i(String m, int p, int s, int v, int x, int t) {		
		if (m.length()>4) {
			switch(m.charAt(4)) {
				case 'q': return t==w ? wqpm : bqpm;
				case 'r': return t==w ? wrpm : brpm;
				case 'b': return t==w ? wbpm : bbpm;
				case 'n': return t==w ? wnpm : bnpm;
				default: return move;
			}				
		} else if (p == wk && s == e1 && (v == g1 || v == h1)) {
			return cast;
		} else if (p == wk && s == e1 && (v == c1 || v == a1)) {
			return cast;
		} else if (p == bk && s == e8 && (v == g8 || v == h8)) {
			return cast;
		} else if (p == bk && s == e8 && (v == c8 || v == a8)) {			
			return cast;
		} else if (p == wk || p == bk) {
			return kmov;
		} else if (p == wr && (s == h1 || s == a1)) {
			return rmov;			
		} else if (p == br && (s == h8 || s == a8)) {
			return rmov;			
		} else if (p == wp && (s/8) == 1 && (v/8) == 3) {			
			return pdmo;
		} else if (p == wp && (s/8) == 4 && x == 0 && ((v-s) == 9 || (v-s) == 7)) {
			return ecap;		
		} else if (p == bp && s/8==6 && v/8==4) {
			return pdmo;
		} else if (p == bp && s/8==3 && x==0 && ((s-v)==9 || (s-v)==7)) {
			return ecap;		
		} else {
			return move;
		}	
	}
}
