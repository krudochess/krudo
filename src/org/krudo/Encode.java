/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo;

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
	
}
