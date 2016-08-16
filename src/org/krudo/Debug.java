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
import static org.krudo.Config.*;
import static org.krudo.Decode.*;
import static org.krudo.Describe.*;

//
public final class Debug 
{
	//
	public static boolean DEBUG_SHOW_MOVE_WEIGHT = false;
	
	//
	public static boolean DEBUG_SHOW_ALGEBRIC = false;
	
    //
    public static int count_enpassant = 0;
    
    //
    public static int count_captures = 0;

    //
    public static int count_incheck = 0;
        
    //
	public final static void dump(final PV pv) { print(desc(pv)); }
    
	//
	public final static void dump(final Line l) { print(desc(l)); }

	// 
	public final static void dump(final Move m) { print(desc(m)); }
	
    // 
	public final static void dump(final Capture m) { print(desc(m)); }
			
	//
	public final static void dump(final Node n) { print(desc(n)); }
	
	//
	public final static void dump(final Node n, final Move m) {	print(desc(n, m)); }
	
	//
	public final static void dump(final Move m,	final Node n) { print(desc(m, n)); }
		
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
        if (s.length == 64)
        {     
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
        else 
        {
            //
            for (int i = 0; i < s.length; i++) 
            {
                //
                String sep =  " ";

                //
                System.out.print(s[i]+sep);     
            }
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
		n.legals();
		
		for(int l=0; l<n.legals.i; l++) {
			echo(
				//pad(i2m(n.m.s[l],n.m.v[l],n.m.k[l],n.n.B[n.m.s[l]]),5),
				pad(n.legals.w[l],5)
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
            /*
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
			}*/
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
        n.legals();
        
        //
        Move m = n.legals.sort().twin();
        
        
        
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
        final int l = m.i;
        
        //
        for (int i = 0; i < l; i++) 
        {	
            //
            if (m.k[i] == ECAP) { count_enpassant++; }

            //
            if (n.B[m.v[i]] != O) { count_captures++; }                        
                        
            //
            n.domove(m, i);		
            
            //
            //if () { count_incheck++; }
            
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
            Krudo.CONSOLE.print("assertPieceCount fails");
            //Krudo.CONSOLE.print(desc(n));
            //Krudo.CONSOLE.print(desc(n.L));
            n.unmove();
            Krudo.CONSOLE.print(desc(n));
            //CACHE_LEGALS = false;
            n.legals();
            Krudo.CONSOLE.print(desc(n.legals));
            //print(Book.list(n.phk));
            Krudo.CONSOLE.close();
            
            //java.lang.Thread.dumpStack();
            
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
