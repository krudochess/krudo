package org.krudo.util;

//
import org.krudo.Line;
import org.krudo.Move;

//
import static org.krudo.Const.*;

//
public final class Trans {

	//
	public static final String i2s(int s) {
		return ""+(char)('a'+s%8)+(char)('1'+s/8);		
	}  
	
	//
	public static final int i2s(int r, int c) {
		return r * 8 + c;
	}
	
	//
	public static final String i2p(int piece) {
		switch(piece) {
			case 0:  return ".";			
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
			case K:  return "K";	
			case Q:  return "Q";	
			case R:  return "R";	
			case B:  return "B";	
			case N:  return "N";	
			case P:  return "P";				
			default: return "?";	
		}	
	} 
	
	//
	public static final String i2f(int piece) {
		switch(piece) {
			case 0:  return ".";			
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
			case K:  return "K";	
			case Q:  return "Q";	
			case R:  return "R";	
			case B:  return "B";	
			case N:  return "N";	
			case P:  return "P";				
			default: return "?";	
		}	
	} 
	
	//
	public static final String i2k(int k) {
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
		}	
	}
	
	//
	public static final String i2t(int t) {
		switch(t) {
			case w: return "w";
			case b: return "b";		
			default: return "?";	
		}	
	}
	
	//
	public static final int s2i(String s) {
		return s2i(s.charAt(0),s.charAt(1));
	}
	
	//
	public static final int s2i(char c0, char c1) {
		return (int)(c1-'1')*8 + c0-'a';
	}
	
	//
	public static final String i2m(int s,int v) {
		return i2s(s)+i2s(v);
	}
	
	//
	public static final String i2m(int s, int v, int k) {
		String p = "";
		switch(k) {
			case Q:p="q";break;
			case R:p="r";break;
			case B:p="b";break;
			case N:p="n";break;
		}			
		return i2s(s)+i2s(v)+p;
	}
	
	//
	public static final String i2m(int s, int v, int k, int p) {
		String m = "";
		switch(p) {
			case wp: case bp: break;
			case wn: case bn: m+="N"; break;	
			case wb: case bb: m+="B"; break;	
			case wr: case br: m+="R"; break;	
			case wq: case bq: m+="Q"; break;	
			case wk: case bk: m+="K"; break;	
		}
		m += i2s(v);		
		return m;
	}
	
	//
	public static final String i2m(Move m) {
		String o = "";
		String s = "";		
		for(int i=0; i<m.l; i++) {
			o+= s + i2m(m.s[i],m.v[i],m.k[i]); 
			s = " ";
		}
		return o;
	}
	
	//
	public static final String i2m(Move m, int i) {
		return i2m(m.s[i],m.v[i],m.k[i]);
	}
	
	//
	public static final String i2m(Move m, int i, int k, int p) {
		return i2m(m.s[i],m.v[i],m.k[i],p);
	}
	
	//
	public static final String i2m(Line m, int f, int t) {
		String o = "";
		String s = "";		
		for(int i=f; i<t; i++) {
			o+= s + i2m(m.s[i],m.v[i],m.k[i]); 
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
		return s%8==c;
	}
	
	//
	public static final int s2w(int s) {
		return s/8;
	}
	
	//
	public static final int s2b(int s) {
		return 7-s/8;
	}
	
	// return "k" for move passed  
	public static final int k2i(String m, int p, int s, int v, int x, int t) {		
		if (m.length()>4) {
			switch(m.charAt(4)) {
				case 'q': return x==0 ? qpmo|t : qpca|t;
				case 'r': return x==0 ? rpmo|t : rpca|t;
				case 'b': return x==0 ? bpmo|t : bpca|t;
				case 'n': return x==0 ? npmo|t : npca|t;
				default: return x==0 ? move|t : capt|t;
			}				
		} else if ((p==wk) && s==e1 && (v==g1||v==h1)) {
			return wksc;
		} else if ((p==wk) && s==e1 && (v==c1||v==a1)) {
			return wqsc;
		} else if ((p==bk) && s==e8 && (v==g8||v==h8)) {
			return bksc;
		} else if ((p==bk) && s==e8 && (v==c8||v==a8)) {			
			return bqsc;
		} else if ((p==wk) && x==0) {
			return wkmo;
		} else if ((p==wk) && x!=0) {
			return wkca;
		} else if ((p==bk) && x==0) {
			return bkmo;
		} else if ((p==bk) && x!=0) {
			return bkca;			
		} else if ((p==wr) && x==0) {
			return wrmo;
		} else if ((p==wr) && x!=0) {
			return wrca;
		} else if ((p==br) && x==0) {
			return brmo;
		} else if ((p==br) && x!=0) {
			return brca;			
		} else if ((p==wp) && s/8==1 && v/8==3) {			
			return wpdm;
		} else if ((p==wp) && s/8==4 && x==0 && ((v-s)==9 || (v-s)==7)) {
			return weca;		
		} else if ((p==bp) && s/8==6 && v/8==4) {
			return bpdm;
		} else if ((p==bp) && s/8==3 && x==0 && ((s-v)==9 || (s-v)==7)) {
			return beca;		
		} else if (x!=0) {
			return capt|t;
		} else {
			return move;
		}	
	}
	
}
