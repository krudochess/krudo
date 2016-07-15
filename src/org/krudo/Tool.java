/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo;

//
import java.io.File;
import java.util.Random;

//
public final class Tool {

	// random int between min and max with min and max included
	public static final int rand(int min, int max) {
		
		//
		return new Random().nextInt((max - min) + 1) + min;
	}
	
	// random int between min and max with min and max included
	public static final int rand() {
		
		//
		return new Random().nextInt();
	}
	
	//	
	public static final boolean mask(int v, int m) {
		
		//
		return (v & m) == m;
	}

	//
	public static final boolean mask(int v, int m, int e) {
		
		//
		return (v&m)==e;
	}
	
	//
	public static final void echo(Object arg) {
		
		//
		System.out.print(arg);				
	}
	
	//
	public static final void echo(Object... args) {
		
		//
		String sep = "";
		
		//
		for(Object arg: args) {			
			
			//
			System.out.print(sep);
			
			//
			System.out.print(arg);
			
			//
			sep = " ";
		}				
	}
	
	//
	public static final void print(Object arg) {
		
		//
		System.out.print(arg);		
		
		//
		System.out.print("\n");
	}
	
	//
	public static final void print(Object... args) {
		
		//
		String sep = "";
		
		//
		for (Object arg: args) {
			
			//
			System.out.print(sep);
			
			//
			System.out.print(arg);
			
			//
			sep = " ";
		}	
		
		//
		System.out.print("\n");
	}
	
	//
	public static final void keys(Object... args) {
		String sep = "";
		boolean k = true;
		for(Object arg: args) {		
			if (k) {
				System.out.print(sep+rpad(arg,6));
			} else {
				System.out.print(sep+lpad(arg,4));
			}
			sep = " ";			
			k=!k;
		}
		System.out.print("\n");
	}
	
	//
	public static final String path(String f) {
		return (System.getenv().containsKey("ENGINEDIR") ? System.getenv().get("ENGINEDIR") : System.getProperty("user.dir"))+File.separator+f;	
	}
	
	//
	public static final long byte2long(byte[] b, int o, int l) 
    {
		long v = 0L;
		for (int i = o; i < o + l; i++) {
		   v = (v << 8L) | (b[i] & 0xffL);
		}
		return v;
	}
	
	//
	public static final int byte2int(byte[] b, int o, int l) 
    {
		int v = 0;
		for (int i = o; i < o + l; i++) {
		   v = (v << 8) + (b[i] & 0xff);
		}
		return v;
	}
	
	//
	public static final String hex(long l) {
		return String.format("%016X",l).toLowerCase();
	} 
	
	//
	public static final String bin(int i) {
		return String.format("%1$5s",Integer.toBinaryString(i));
	} 
	
	//
	public static final String bin(int i, int l) {
		return String.format("%1$"+l+"s",Integer.toBinaryString(i)).replace(' ', '0');
	} 
	
	//
	public static final String pad(Object o, int w) {
		String t = ""+o;
		return String.format("%1$" + w + "s", t);		
	}
	
	//
	public static final String rpad(Object o, int w) {
		String t = ""+o;
		return String.format("%1$" + w + "s", t);		
	}
	
	//
	public static final String lpad(Object o, int w) {
		String t = ""+o;
		return String.format("%1$-" + w + "s", t);		
	}
	
	//
	public static final boolean empty(String s) {
		return s == null || s.equals("");
	}
	
	//
	public static final boolean has(String s) {
		return s != null && !s.equals("");
	}
	
	//
	public static final long toLong(String s) {
		return Long.parseLong(s);
	}
	
	//
	public static final long toInt(String s) {
		return Integer.parseInt(s);
	}
	
	//
	public static final long time() {
		return System.currentTimeMillis();
	}
	
	//
	public static final void exit(String msg) {
		echo(msg);
		System.exit(-1);		
	}
	
	//
	public static final void exit() {
		System.exit(-1);		
	}
    
    public static final String info() 
    {
        String bit = System.getProperty("sun.arch.data.model"); 
            
        System.out.println(System.getenv("PROCESSOR_IDENTIFIER"));
System.out.println(System.getenv("PROCESSOR_ARCHITECTURE"));
System.out.println(System.getenv("PROCESSOR_ARCHITEW6432"));
System.out.println(System.getenv("NUMBER_OF_PROCESSORS"));
        
        System.out.println(Runtime.getRuntime().availableProcessors());
        String cmd = "wmic cpu get name";
        
        try {
         java.util.Scanner s = new java.util.Scanner(Runtime.getRuntime().exec(cmd).getInputStream()).useDelimiter("\\A");
        String output = s.hasNext() ? s.next() : "";
        print(output);
        }catch (java.io.IOException ioe) {
        } 
        
        
        return System.getenv("PROCESSOR_IDENTIFIER")+" ("+bit+"bit)";
    }
}