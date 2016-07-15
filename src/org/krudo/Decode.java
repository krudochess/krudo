/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo;

//
import org.krudo.Line;
import org.krudo.Move;

//
import static org.krudo.Constant.*;
import static org.krudo.Debug.*;
import static org.krudo.Encode.*;

//
public final class Decode 
{
	// square to string
	public static final String s2s(
		final int s // square
	) {		
		//
		return ""+(char)('a'+s%8)+(char)('1'+s/8);		
	}  
	
	// piece to string
	public static final String p2s(
		final int p // piece
	) {		
		//
		switch(p) {
			case O:  return ".";			
			case wp: return "P";	
			case wn: return "N";	
			case wb: return "B";	
			case wr: return "R";	
			case wq: return "Q";	
			case wk: return "K";	
			case bp: return "x";	
			case bn: return "n";	
			case bb: return "b";	
			case br: return "r";	
			case bq: return "q";	
			case bk: return "k";						
			default: return "?";	
		}	
	} 
	
	// figure to string
	public static final String f2s(int piece) {
		switch(piece) {
			case O:  return ".";			
			case wp: return "P";	
			case wn: return "N";	
			case wb: return "B";	
			case wr: return "R";	
			case wq: return "Q";	
			case wk: return "K";	
			case bp: return "P";	
			case bn: return "N";	
			case bb: return "B";	
			case br: return "R";	
			case bq: return "Q";	
			case bk: return "K";					
			default: return "?";	
		}	
	} 
	
	// kind-of-move to string
	public static final String k2s(int k) {
		/*
		switch(k) {
			case 0:	   return ".";
			case move: return "move";		
			case wpdm: return "wpdm";	
			case bpdm: return "bpdm";							
			case weca: return "weca";			
			case beca: return "beca";			
			case wksc: return "wksc";
			case wqsc: return "wqsc";
			case bksc: return "bksc";
			case bqsc: return "bqsc";				
			case wkmo: return "wkmo";
			case bkmo: return "bkmo";				
			case wkca: return "wkca";
			case bkca: return "bkca";				
			case wrmo: return "wrmo";
			case brmo: return "brmo";				
			case wrca: return "wrca";
			case brca: return "brca";				
			case wcap: return "wcap";
			case bcap: return "bcap";				
			default: return "?";	
		}*/
		return "?";
	}
	
	//
	public static final String i2t(int t) 
    {
		switch(t) 
        {
			case w: return "w";
			case b: return "b";		
			default: return "?";	
		}	
	}
	
	//
	public static final int s2i(String s) 
    {
		return cr2i(s.charAt(0), s.charAt(1));
	}
	
	//
	public static final String i2m(int s,int v) 
    {
		return s2s(s)+s2s(v);
	}
	
	// move to string
	public static final String m2s(int s, int v, int k)
    {
		// promote symbol
        String piece = "";
		
        //
        switch (k) 
        {
			case wqpm:
            case bqpm: piece = "q"; break;
            case wrpm:
            case brpm: piece = "r"; break;
            case wbpm: 
            case bbpm: piece = "b"; break;
            case wnpm:
            case bnpm: piece = "n"; break;
		}		
        
        //
		return s2s(s) + s2s(v) + piece;
	}
	
	// move to string
	public static final String m2s(int s, int v, int k, int w) 
    {
		//
		String move = m2s(s, v, k);

		//
		if (DEBUG_SHOW_MOVE_WEIGHT) {
			move += String.format("=%+d", w);
		}
				
		//
		return move;
	}
	
	//
	public static final String i2m(int p, int s, int v, int k) {
		
		//
		String m = "";
		
		//
		switch(p) {
			case wp: case bp: break;
			case wn: case bn: m += "N"; break;	
			case wb: case bb: m += "B"; break;	
			case wr: case br: m += "R"; break;	
			case wq: case bq: m += "Q"; break;	
			case wk: case bk: m += "K"; break;	
		}
		
		//
		m += s2s(v);		
		
		//
		return m;
	}
	
	//
	public static final String i2m(Move m) {
		String o = "";
		String s = "";		
		for(int i=0; i<m.i; i++) {
			o+= s + m2s(m.s[i],m.v[i],m.k[i]); 
			s = " ";
		}
		return o;
	}
	
	//
	public static final String i2m(Move m, int i) {
		return m2s(m.s[i],m.v[i],m.k[i]);
	}
	
	//
	public static final String i2m(Move m, int i, int k, int p) {
		return i2m(m.s[i],m.v[i],m.k[i],p);
	}
	
	//
	public static final String i2m(Line line) {
		String o = "";
		String s = "";		
		for(int i=0; i<line.i; i++) {
			o+= s + m2s(line.s[i],line.v[i],line.k[i]); 
			s = " ";
		}
		return o;
	}
	
	//
	public static final String i2m(Line m, int f) {
		String o = "";
		String s = "";		
		//for(int i=f; i<m.i; i++) {
		//	o+= s + i2m(m.s[i],m.v[i],m.k[i]); 
		//	s = " ";
		//}
		return o;
	}
	
	//
	public static final int i2e(int s) {
		return ((16-(s%8-4)*(s%8-3))*(16-(s/8-3)*(s/8-4)))/10;	
	}

	//
	public static final boolean eqc(int s, int c) {
		return s % 8 == c;
	}
	
	//
	public static final int s2w(int s) {
		return s/8;
	}
	
	//
	public static final int s2b(int s) {
		return 7-s/8;
	}
	
	
	
}
