/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.util;

//
import static org.krudo.Const.b;
import static org.krudo.Const.bp;
import static org.krudo.Const.cast;
import static org.krudo.Const.ksca;
import static org.krudo.Const.qsca;
import static org.krudo.Const.w;
import static org.krudo.Const.wp;
import org.krudo.Line;
import org.krudo.Move;
import org.krudo.Node;
import static org.krudo.util.Tools.echo;
import static org.krudo.util.Tools.keys;
import static org.krudo.util.Tools.lpad;
import static org.krudo.util.Tools.rpad;
import static org.krudo.util.Trans.i2f;
import static org.krudo.util.Trans.i2m;
import static org.krudo.util.Trans.i2p;
import static org.krudo.util.Trans.i2s;
import static org.krudo.util.Zobrist.hash;

/**
 *
 * @author cicciodarkast
 */
public class Describe {

	
	public final static String desc(Node n, Move m, int i) {
	
		String a = "";
			
		int p = n.B[m.s[i]];
		int x = n.B[m.v[i]];

		if ((m.k[i] & cast) != cast) {
			if (p!=wp && p!=bp) {
				a += i2f(p);
			} 

			if (x != 0) {
				a += "x";
			}

			a += i2s(m.v[i]);

			/*
			if (x != 0) {
				a += " ("+i2f(x)+")";
			}
			*/
		} 

		//
		else if ((m.k[i]&ksca) == ksca) {
			a += "O-O";
		} 

		//
		else if ((m.k[i]&qsca) == qsca) {
			a += "O-O-O";
		}

		//
		n.domove(m,i);
		if (n.t==w?n.black_attack(n.wks):n.white_attack(n.bks)) {
			a += "+";
		}
		n.unmove();
		
		//
		return a;	
	}

	//
	public final static String desc(Object arg) {

		if (arg instanceof Node) {
			return Describe.desc((Node) arg);
		} else if (arg instanceof Move) {
			return Describe.desc((Move) arg);
		} else {		
			return arg.toString();
		}
	}
	
	//
	public final static String desc(Node n) {
	
		//
		String desc = "";
		
		//
		for (int r = 7; r >= 0; r--) {
					
			//
			for(int c = 0; c < 8; c++) {
				desc += i2p(n.B[i2s(r, c)]) + " ";
			}
			
			//
			desc += (r==0 && n.t==w || r==7 && n.t==b ? "<" : " ");
			
			/*
			switch(r) {
				case 0: keys("e:",i2s(n.e),"c:",Integer.toBinaryString(n.c)); break;
				case 1: keys("cw:",n.cw,"cb:",n.cb); break;
				case 2: keys("wks:",i2s(n.wks),"bks:",i2s(n.bks)); break;
				case 3: keys("wrs:",i2s(n.wks),"brs:",i2s(n.bks)); break;
				case 4: keys("ph:",n.cw,"ew:",n.wks); break;
				case 5: keys("wpw:",n.wks,"bpw:",n.wks); break;
				case 6: keys("hm:",n.hm,"n:",n.n); break;
				case 7: keys("h:",Long.toHexString(hash(n))); break;					
			}*/
			
			//
			desc += "\n";
		}
		
		//
		return desc;
	}
	
	//
	public final static void desc(Line l) {
		/*
		//
		String o = "";
		
		//
		int n = 1;
		
		//
		for(int i=0; i<l.i; i++) {
			
			if (mask(l.p[i],t,w)) {
				o += n+".";				
			}
			
			if (l.p[i]!=wp && l.p[i]!=bp) {
				o += i2f(l.p[i]);
			}
			
			o += i2s(l.v[i]);
			
			o += " ";
			
			if (mask(l.p[i],t,b)) {
				n++;				
			}			
		}
			
		//
		echo(o);*/
	}
	
	//
	public final static String desc(Move m) {
	
		//
		String desc = "";
		
		//
		String sepa = "";
		
		//
		for(int i=0; i < m.i; i++) {
			desc += sepa + i2m(m.s[i], m.v[i], m.k[i]) + " ";
			sepa = i%6 == 5 ? "\n" : " ";
		}	
		
		//
		return desc;
	}
	
	//
	public final static void desc(Node n, Move m) {
		
		String o = "";

		//
		for(int i=0; i<m.l; i++) {
			
			
			
			String a = "";
			
			int p = n.B[m.s[i]];
			int x = n.B[m.v[i]];
			
			if ((m.k[i] & cast) != cast) {
				if (p!=wp && p!=bp) {
					a += i2f(p);
				} 

				if (x != 0) {
					a += "x";
				}

				a += i2s(m.v[i]);
				
				if (x != 0) {
					a += " ("+i2f(x)+")";
				}
			} 
			
			//
			else if ((m.k[i]&ksca) == ksca) {
				a += "O-O";
			} 
			
			//
			else if ((m.k[i]&qsca) == qsca) {
				a += "O-O-O";
			}
			
			//
			o += lpad(a,8) + rpad(m.w[i],4)+"\n";;
		}
		
		echo(o);
	}
	
}
