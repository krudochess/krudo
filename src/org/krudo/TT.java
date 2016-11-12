/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

// 
package org.krudo;

//
import static org.krudo.Tool.*;
import static org.krudo.Debug.*;
import static org.krudo.Config.*;

//
import java.util.Map;
import java.util.LinkedHashMap;

//
public final class TT 
{        
    //
    public final static int EXAC = 1;
    
    //
    public final static int MAX_EMPTY = -99999;
    
    //
    public final static int MIN_EMPTY = +99999;
        
    //
    public final static int TT_SIZE = 200000;
    
    //
    public final static int TT_DEEP = 10;
    
    //
    public final static TT_CACHE CACHE = new TT_CACHE(); 
    
    //
    public final static TT_SHEET[] STACK = new TT_SHEET[TT_SIZE + 10]; 
    
    //
    public static TT_SHEET probe;
    
    //
    public static long index;
    
    //
    public static int count;
    
    //
    public static int score;
    
    //
    public static int queries = 0;
        
    //
    public static int success = 0;
               
    //
    public static class TT_SHEET 
    {
        //
        public final int[] max = new int[TT_DEEP]; 
        
        //
        public final int[] min = new int[TT_DEEP]; 
        
        //
        public TT_SHEET() { clear(); }
        
        //
        public final void clear() 
        {
            //
            for (int i = 0; i < TT_DEEP; i++) 
            {
                //
                max[i] = MAX_EMPTY;
                
                //
                min[i] = MIN_EMPTY;                   
            }                
        }        
    }
    
    //
    public static class TT_CACHE extends LinkedHashMap<Long,TT_SHEET>
    {       
        //
        public TT_CACHE()
        {
            super(TT_SIZE, 0.95f, true);        
        }
        
        //
        @Override
        protected boolean removeEldestEntry(Map.Entry<Long, TT_SHEET> e) 
        {
            //
            if (size() > TT_SIZE) 
            {
                //
                //e.getValue().clear();
                
                //
                STACK[count++] = e.getValue();
                
                //
                return true;
            }
            
            //
            return false; 
        }
        
        //
        public final void add(long h, TT_SHEET s)
        {           
            //
            super.put(h, s);                
        }

        //
        public final boolean has(long h) 
        {   
            //
            return super.containsKey(h);             
        }

        //
        public final TT_SHEET get(long h)
        {
            //
            return super.get(h);            
        }
    }
    
    //
    public final static void init()
    {
        //
        if (!SEARCH_TT) { return; } 
        
        //
        for(int i = 0; i < TT_SIZE + 10; i++)
        {
            STACK[i] = new TT_SHEET();        
        }
        
        //
        count = TT_SIZE + 10;    
    }
            
    //
    public final static boolean probe(long h, int d, int a, int b) 
    {
        //
        if (!SEARCH_TT) { return false; }
        
        //
        queries++;
        
        //
        if (probeset(h) && probe.min[d] != MIN_EMPTY) 
        {
            if (probe.max[d] <= a) {
                score = a;
            } else {            
                score = probe.max[d];
            }
            
            success++;
            
            
            //print("probemin", hex(h), "load", probe.min[d]);
            
            //exit();
            return true;
        }
        
        //
        return false;
    }
    
    //
    public final static boolean probeset(long h)
    {
        //
        if (!SEARCH_TT) { return false; }
        
        //
        index = h;
        
        //
        if (CACHE.has(h)) 
        { 
            //
            probe = CACHE.get(h);
            //print("probeset", hex(h), "success");
            
            //dump(probe.min);
            //print("");
            
            //dump(probe.max);
            
            //print("");
            
            //exit();
            
           
            //
            return true;
        } 
        
        //
        probe = STACK[--count];
        
        //
        CACHE.add(index, probe);
        
        //
        return false;        
    }
    
    //
	public final static void store(long h, int f, int d, int s) 
    {	
        //
        if (!SEARCH_TT) { return; }
        
        //
        probe.max[d] = s;
                
        //
        //print("storemax:", d, hex(index), s);        
	}
	        
    //
    public final static void info()
    {
        //        
        print("TT info: (free:"+count+" q:"+queries+" s:"+success+")");            
    }
}
