/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.eval;

//
import static org.krudo.Constant.*;
import static org.krudo.util.Debug.*;
import static org.krudo.util.Tool.*;
import static org.krudo.util.Decode.*;

//
import org.krudo.*;

//
public class Eval1 
{
    public final static int[] a = new int[] {1,2,3}; 
    
    public final static int[] b = new int[] {5,5,5}; 
    
    static {
        b[0] = b[0] - a[0];
    }
    
	//
	public static void main(String[] args) 
    {
/*
        int o = -100;
        int f = 200 - o;
        
        int p = 250;
        
        //print(o + (p * f >> 8));
        print(b[0]);
        int m = 0b11;
        
        print(bin(wq));
        print(bin(bq));
        print(bin(wr));
        print(bin(wb));
        print(bin(wn));
        print(wn & m);*/
        print(Eval.OPW[bp&lo][e5]);
	}
}

