/**
 * Krudo 0.16a 
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo;

//
public final class Config {
	
	// config flags
	public final static boolean 
			
	// enable/disable evaluation 
	// of legal moves generated
	LEGALS_EVAL = false,		
	
			
			
	NODE_EVAL		= true,
	EVAL_CACHE		= false,
	MOVE_EVAL		= true, // use threats in run-time
	MOVE_SORT		= true, // use threats in run-time
	MOVE_CACHE		= true,	// use caching systems (TT, Zobrist, ecc...)
	MOVE_BUFFER		= true,
	MOVE_LEGALS		= true,
	SEARCH_CUT_OFF	= true,
	PSEUDO_REMAPS	= false,
	THREAD			= false, // use threats in run-time
	TABLE			= false, // use threats in run-time
	ASPIRATION		= false;
}
