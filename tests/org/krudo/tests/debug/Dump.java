/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.krudo.tests.debug;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import org.krudo.Capture;
import static org.krudo.Decode.s2s;
import static org.krudo.Describe.desc;
import org.krudo.Legals;
import org.krudo.Line;
import org.krudo.Move;
import org.krudo.Moves;
import org.krudo.Node;
import org.krudo.PV;
import org.krudo.Search;
import static org.krudo.Tool.print;

/**
 *
 * @author francescobianco
 */
public class Dump {
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
	public final static void dump(final Search s) 
    { 
        //
        print(desc(s.node)); 
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
    public final static void dump(final Moves moves) 
    {        
        try {
            Field f = moves.getClass().getDeclaredField("count");
            f.setAccessible(true);
            int count = (Integer) f.get(moves);
            //int verified = count + Legals.size();
            //print("Moves free="+count+" cache="+Legals.size()+" verified="+verified);
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
}
