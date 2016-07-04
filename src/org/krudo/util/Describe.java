/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.util;

//
import org.krudo.Line;
import org.krudo.Move;
import org.krudo.Node;

//
import static org.krudo.Constant.*;
import static org.krudo.util.Debug.*;
import static org.krudo.util.Tool.*;
import static org.krudo.util.Encode.*;
import static org.krudo.util.Decode.*;

/**
 *
 * @author cicciodarkast
 */
public class Describe
{
	//
	public final static String desc(
		final Move m, 		
		final int i,
		final Node n
	) {	
		//
		String move = "";
		
		//
		final int p = n.B[m.s[i]];
		
		//
		final int x = n.B[m.v[i]];

		//
		if (m.k[i] != cast) {
			
			if (p != wp && p != bp) 
            {
				move += f2s(p);
			} 

			if (x != 0) 
            {
				move += "x";
			}

			move += s2s(m.v[i]);

			/*
			if (x != 0) {
				a += " ("+i2f(x)+")";
			}
			*/
		} 

		//
		else if (false) 
        {
			move += "O-O";
		} 

		//
		else if (false) 
        {
			move += "O-O-O";
		}

		//
		//n.domove(m,i);
		//if (n.t==w?n.black_attack(n.wks):n.white_attack(n.bks)) {
		//	a += "+";
		//}
		//n.unmove();
		
		//
		if (DEBUG_SHOW_WEIGTH) 
        {
			move += String.format("(%+d)", m.w[i]);
		}
		
		//
		return move;	
	}

	//
	public final static String desc(Node n) 
    {
		//
		String desc = "";
		
		//
		for (int r = 7; r >= 0; r--) 
        {			
			//
			for(int c = 0; c < 8; c++) 
            {
				desc += p2s(n.B[cr2i(c, r)]) + " ";
			}
			
			//
			desc += (r==0 && n.t==w || r==7 && n.t==b ? "<  " : "   ");
			
			//
			switch (r)
            {
				//case 0: keys("e:",i2s(n.e),"c:",Integer.toBinaryString(n.c)); break;
				//case 2: keys("wks:",i2s(n.wks),"bks:",i2s(n.bks)); break;
				//case 3: keys("wrs:",i2s(n.wks),"brs:",i2s(n.bks)); break;
				//case 4: keys("ph:",n.cw,"ew:",n.wks); break;
				//case 5: keys("wpw:",n.wks,"bpw:",n.wks); break;
				//case 6: keys("hm:",n.hm,"n:",n.n); break;
				case 1: desc += "cw: "+n.cw+"  cb: "+n.cb; break;
				case 0: desc += "h: " + Long.toHexString(n.h); break;					
			}
			
			//
			desc += "\n";
		}
		
		//
		return desc;
	}
	
	//
	public final static void desc(Line l) 
    {
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
	public final static String desc(Move m) 
    {
		//
		String desc = "";
		
		//
		String sepa = "";
		
		//
		for (int j=0; j < m.i; j++) {
			
			//
			String move = desc(m, j);
										
			//
			int cell = DEBUG_SHOW_WEIGTH ? 10 : 6;
			
			//
			desc += sepa + lpad(move, cell);
			
			//
			sepa = j % 6 == 5 ? "\n" : " ";
		}	
		
		//
		return desc;
	}
	
	//
	public final static String desc(Move m, Node n) {
	
		//
		String desc = "";
		
		//
		String sepa = "";
		
		//
		for (int j = 0; j < m.i; j++) {
			
			//
			String move = desc(m, j);
										
			//
			int cell = DEBUG_SHOW_WEIGTH ? 10 : 6;
			
			//
			desc += sepa + lpad(move, cell);
			
			//
			sepa = j % 6 == 5 ? "\n" : " ";
		}	
		
		//
		return desc;
	}
	
	//
	public final static String desc(Node n, Move m) {
	
		//
		String desc = "";
		
		//
		for (int r = 0; r < 8; r++) {
			
			//
			for (int c = 0; c < 8; c++) {
				desc += p2s(n.B[(7-r)*8+c]) + " ";
			}
			
			//
			desc += r==0 && n.t==b || r==7 && n.t==w ? "<  " : "   ";
			
			//
			for(int i = r * 4; i < r * 4 + 4; i++) {
				if (i < m.i) {
					desc += lpad(desc(m, i), 11);
				}
			}
			
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
			}
			*/
			
			desc += "\n";
		}		
					
		//
		return desc;
	}
		
	//
	public final static String desc(		
		final Move m, 
		final int i
	) {
		//
		return m2s(m.s[i], m.v[i], m.k[i], m.w[i]);
	}
}
