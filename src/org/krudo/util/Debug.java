/**
 * Krudo 0.16a 
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.util;

//
import org.krudo.Line;
import org.krudo.Move;
import org.krudo.Node;

//
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.FileInputStream;
import java.util.regex.*;

//
import static org.krudo.Constant.*;
import org.krudo.Moves;
import static org.krudo.util.Tool.*;
import static org.krudo.util.Decode.*;
import static org.krudo.util.Describe.*;
import static org.krudo.util.Zobrist.hash;

//
public final class Debug {

	//
	public static boolean DEBUG_SHOW_WEIGTH = false;
	
	//
	public static boolean DEBUG_SHOW_ALGEBRIC = false;
	
	//
	public final static void dump(int d, Move m) {		
	
		//
		String l = d + " " + m.i + " ";
		
		//
		for (int i = 0; i < m.i; i++) {
			l += " "+ m2s(m.s[i], m.v[i], m.k[i]);
		}	
		
		//
		echo(l);
	}

	//
	public final static void dump(Line l) 
    {		
		String desc = desc(l);
        
        print(desc);
	}

	
	
	//
	public final static void dump(Move m) 
    {					
		//
		String s = desc(m);
		
		//
		print(s);
	}
	
	//
	public final static void dump(Move[] pv) 
    {				
		for (int j=0; j<pv.length; j++) {
			Move m = pv[j];
			String d = "";
			String s = "";
			for(int i=0;i<m.i;i++) {
				d += s + m2s(m.s[i],m.v[i],m.k[i])+" ("+k2s(m.k[i])+m.w[i]+")" ;
				s = i%10==9 ? "\n" : " ";
			}	
			echo("("+j+")",d);
		}
	}
	
	//
	public final static void dump(Move m, int i) {		
		echo(m2s(m.s[i],m.v[i],m.k[i])+" ("+k2s(m.k[i])+m.w[i]+")");		
	}

	//
	public final static void dump(Node n) 
    {
        String desc = desc(n);
        
        print(desc);
		/*
        for(int r=0;r<8;r++) {
			for(int c=0;c<8;c++) {
				System.out.print(p2s(n.B[(7-r)*8+c])+" ");
			}
			System.out.print(r==0 && n.t==b || r==7 && n.t==w ? "<" : " ");
			
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
			/*
			System.out.print("\n");
		}		
		
		//
		System.out.print("\n");*/
	}
	
	//
	public final static void dump(
		final Node n,
		final Move m	
	) {						
		print(desc(n, m));
	}
	
	//
	public final static void dump(
		final Move m,	
		final Node n
	) {						
		print(desc(m, n));
	}
		
	//
	public final static void dump(
		final Exception e
	) {
		StringWriter s = new StringWriter();
		e.printStackTrace(new PrintWriter(s));
		System.out.print(s.toString()); 	
	}
	
	//
	public final static void dump(
		final int[] s
	) {	
		for (int i=0; i<s.length; i++) {
			System.out.print(s2s(s[i])+" "); 			
		}				
	}
	
	//
	public final static void table(Move moves) {
		for(int l=0; l<moves.i; l++) {
			echo(i2m(moves,l),moves.w[l]);		
		}	
	}
	
	//
	public final static void tune(Node n) {
		
		dump(n);
		echo("-------------");
		Move m = n.legals();
		
		for(int l=0; l<m.i; l++) {
			echo(
				pad(i2m(m.s[l],m.v[l],m.k[l],n.B[m.s[l]]),5),
				pad(m.w[l],5)
			);
		}
		
	}
	
	public static final class Perft {
	
		//
		public int n; // nodes
		public int e; // en-passant captures	
		public int c; // castling
		public int x; // captures
		public int h; // checks 
		public int m; // chack-mates

		public long t; // time elapsed
		public long r; // ratio

		//
		public final static void start(Node n, int d, Perft p) {

			long s;
			long e;

			s = System.currentTimeMillis();		
			doing(n,d,p,0,0,0);
			e = System.currentTimeMillis();

			p.t = e - s;
			p.r = p.t>0 ? p.n/p.t : 0;

		}

		//
		public final static void doing(Node n, int d, Perft p, int s, int v, int k) {		
			if (d>0) {			
				Move m = n.legals();
				for(int l=0; l<m.i; l++) {								
					n.domove(m,l);				
					doing(n,d-1,p,m.s[l],m.v[l],m.k[l]);
					n.unmove();
				} 
			} else {
				p.n++;
				
				if (mask(k,ecap)) {
					p.e++;
				}			
				//n.T ^= t;
				//if (n.attack(n.T == wt ? n.bks : n.wks)) {
				//	p.h++;
				//}
				//n.T ^= t;			
			}
		}

	
		//
		public final static void table(Node n, int d) {				
			echo("#     nodes      ms   rx    capt   ec   check");		
			echo("----------------------------------------------");
			for(int i=1; i<=d; i++) {
				Perft p = new Perft();
				start(n,i,p);
				echo(
					pad(i,1),
					pad(p.n,9),
					pad(p.t,7),
					pad(p.r,4),
					pad(p.x,7),
					pad(p.e,4),
					pad(p.h,7)
				);
				// "perft("+d+"): "+c+" ("+String.valueOf(m)+" ms) ["+r+"]";
			}	
		}

	}


	
	//
	public final static String perft(Node n, int d) {
		long s = System.currentTimeMillis();
		long c = doing(n,d);
		long e = System.currentTimeMillis();
		long m = (e - s);
		long r = m>0 ? c/m : 0;
		return "perft("+d+"): "+rpad(c,10)+rpad(String.valueOf(m)+" ms",12)+rpad(r+" kNPS",12);
	}
	
	//
	public final static long doing(Node n, int d) 
    {		
        //
		if (d == 0) { return 1; }
        
        //
        int c = 0;
        
        //
        Move m = n.legals();

        //
        for (int i = 0; i < m.i; i++) 
        {								
            n.domove(m, i);				
            c += doing(n, d-1);
            n.unmove();
        } 

        // 
        Moves.free(m);
        
        //
        return c;
	}
	
	public final static String[][] EPDReader(String f) {
		
		try {
			File file = new File(f);
			FileInputStream fis = new FileInputStream(file);
			byte[] data = new byte[(int) file.length()];
			fis.read(data);
			fis.close();
			String epd = new String(data);
						
			String[] row = epd.split("\\r?\\n");
			String [][] ret = new String[row.length][3];
			
			//
			for(int i=0; i< row.length; i++) {
			
				//
				Pattern p = Pattern.compile("(.*) bm ([^;]+); id \"([^\"]+)\"");
				Matcher m = p.matcher(row[i]);

				//	
				if (m.find()) {
					ret[i][1] = m.group(1);
					ret[i][2] = m.group(2);
					ret[i][0] = m.group(3);
				}				
			}
			
			//
			return ret; 
			
			//echo (epd);
		} catch (Exception e) {
		
		}
		
		return null;
	}
	
    public static void assertPieceCount(Node n) {
    
        int cw = 0;
        int cb = 0;
        
        for (int s=0; s<64; s++) {
        
            if ((n.B[s] & w) == w) {
                cw++;
            }
            
             if ((n.B[s] & b) == b) {
                cb++;
            }
            
        
        
        
        }
        
        if (n.cw != cw || n.cb != cb) {
            print("assertPieceCount fails");
            dump(n);
            dump(n.L);
            exit();
        
        }
    
    
    
    }
    
}
