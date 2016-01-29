package org.krudo.tests;

//
import static org.krudo.util.Debug.*;
import static org.krudo.Const.*;
import static org.krudo.util.Tools.*;
import static org.krudo.util.Trans.*;

//
import org.krudo.*;

//
public class Bench1 {

	//
	public static void main(String[] args) {

		int[] a = new int[100];
		int[] b = new int[100];
		int[] c = new int[100];
		
		int n,m,x,y,o;
		
		long t ;
		
		t = System.currentTimeMillis();
		for(int i=0; i<10000000; i++) {
			x = rand(0,1000);
			y = rand(0,1000);
			n = rand(0,99);
			a[n] = x << 16 | y;
			n = rand(0,99);
			x = a[n]>>16;
			y = a[n]&4;		
			o = x + y;
		}
		echo (System.currentTimeMillis()-t);
		
		t = System.currentTimeMillis();
		for(int i=0; i<10000000; i++) {
			x = rand(0,1000);
			y = rand(0,1000);
			n = rand(0,99);
			b[n] = x;
			c[n] = y;			
			n = rand(0,99);
			x = b[n];
			y = c[n];			 
			o = x + y;
		}
		echo (System.currentTimeMillis()-t);
		
		
		
		/*
		Thinker e = new Thinker();
		
		e.n.domove("e2e4 e7e5".split("\\s"));
		
		dump(e.n.legals());
		
		try {
			e.start();			
		} catch (Exception ex) {
			dump(e.n);
			dump(e.n.L);	
			ex.printStackTrace();
		}
		*/
		
		
		
		
	}
	
}
