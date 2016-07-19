/**
 * Krudo 0.16a 
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo;

//
import org.krudo.*;

//
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.FileInputStream;
import java.util.regex.*;

//
import static org.krudo.Constant.*;
import org.krudo.Moves;
import static org.krudo.Tool.*;
import static org.krudo.Decode.*;
import static org.krudo.Describe.*;

//
public final class Debug 
{
	//
	public static boolean DEBUG_SHOW_MOVE_WEIGHT = false;
	
	//
	public static boolean DEBUG_SHOW_ALGEBRIC = false;
	
    /*
	//
	public final static void dump(int d, Move m)
    {		
		//
		String l = d + " " + m.i + " ";
		
		//
		for (int i = 0; i < m.i; i++)
        {
			l += " "+ m2s(m.s[i], m.v[i], m.k[i]);
		}	
		
		//
		echo(l);
	}
    */
//
	public final static void dump(PV pv) 
    {		
        //        
        print(desc(pv));
	}

    
	//
	public final static void dump(Line l) 
    {		
        //        
        print(desc(l));
	}

	// 
	public final static void dump(Move m) 
    {					
		//
		print(desc(m));
	}
	
    // 
	public final static void dump(Capture m) 
    {					
		//
		print(desc(m));
	}
	
	//
	public final static void dump(Move[] pv) 
    {		
        //
		for (int j=0; j<pv.length; j++) 
        {
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
	public final static void dump(Move m, int i) 
    {		
        //
        echo(m2s(m.s[i],m.v[i],m.k[i])+" ("+k2s(m.k[i])+m.w[i]+")");		
	}

	//
	public final static void dump(Node n) 
    {
        //
        print(desc(n));
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
        //
		for (int i = 0; i < s.length; i++) 
        {
            //
			String sep = i%8==7 ? "\n" : " ";
            
            //
            System.out.print(s2s(s[i])+sep);     
		}				
	}
	
	//
	public final static void table(Move moves) {
		for(int l=0; l<moves.i; l++) {
			echo(i2m(moves,l),moves.w[l]);		
		}	
	}
	
	//
	public final static void tune(Node n) 
    {	
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
	
    //
	public static final class Perft 
    {
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
		public final static void doing(Node n, int d, Perft p, int s, int v, int k) 
        {		
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
		public final static void table(Node n, int d) 
        {				
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
	public final static String perft(Node n, int d) 
    {
        //
		long s = System.currentTimeMillis();
		
        //
        long c = doing(n,d);
		
        //
        long e = System.currentTimeMillis();
		
        //
        long m = (e - s);
		
        //
        long r = m > 0 ? c / m : 0;
		
        //
        return "perft("+d+"): "
             + rpad(c,10)
             + rpad(String.valueOf(m)+" ms",12)
             + rpad(r+" kNPS",12);
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
        
        /*
        //
        if (m.i == 0) {
            print("mate: "+desc(n.L));   
            if (n.L.s[0] == g2) {                
                dump(n);
                dump(n.L);
                print(n.L.i);
                n.unmove();
                dump(n);
                dump(n.L);
                print(n.L.i);
                exit();
            }
        }
        */
        
        //
        for (int i = 0; i < m.i; i++) 
        {	
            //
            n.domove(m, i);		
            
            //
            c += doing(n, d-1);
            
            //
            n.unmove();
        } 

        // 
        Moves.free(m);
        
        //
        return c;
	}
	
    //
	public final static String[][] EPDReader(String f) 
    {
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
	
    //
    public static void assertPieceCount(Node n) 
    {
        int cw = 0;
        int cb = 0;
        
        for (int s=0; s<64; s++)
        {    
            //
            if ((n.B[s] & w) == w) 
            {
                cw++;
            }

            //
            if ((n.B[s] & b) == b) 
            {
                cb++;
            }
        }
        
        if (n.cw != cw || n.cb != cb) {
            print("assertPieceCount fails");
            dump(n);
            dump(n.L);
            n.unmove();
            dump(n);
            dump(n.legals());
            print(Book.list(n.h));
            exit();
        }
    }
    
    public static void slower(int weight) {
    
       int[] m = new int[weight];
       
       for(int j=0;j<weight; j++) {
       

            for(int i=0;i<weight; i++) {

                m[i] = rand(0, weight);
            }
            
            for(int i=0;i<weight; i++) {

                m[j] = m[i];
            }
       }
    
    }
}
