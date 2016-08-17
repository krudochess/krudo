/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo;

//
import java.util.Map;
import java.util.LinkedHashMap;

//
import static org.krudo.Tool.*;
import static org.krudo.Debug.*;
import static org.krudo.Config.*;
import static org.krudo.Describe.*;

//    
public class Legals 
{    
    //
    private static final int LEGALS_CACHE_SIZE = 250000;
    
    //
    private static int queries = 0;
    private static int success = 0;
    
    //
    private final static LinkedHashMap<Long, Move> 
    CACHE = new LinkedHashMap<Long, Move> (LEGALS_CACHE_SIZE, 1.1f, true) 
    {
        @Override
        protected boolean removeEldestEntry(Map.Entry<Long, Move> e) 
        {
            //
            if (!CACHE_LEGALS) { return false; }
            
            // 
            if (size() > LEGALS_CACHE_SIZE) 
            {
                //
                Moves.free(e.getValue());

                // return true than remove
                return true;                                                    
            }
            
            // return false not remove
            return false; 
        }
    };
       
    //
    public final static void add(long h, Move m)
    {           
        //
        if (CACHE_LEGALS) 
        { 
            //
            CACHE.put(h, m);                
        }                 
    }

    //
    public final static boolean has(long h) 
    {    
        //
        if (!CACHE_LEGALS) { return false; }
        
        //
        queries++;
        
        //
        if (CACHE.containsKey(h)) 
        {
            //
            success++;
        
            //
            return true;
        }
        
        //
        return false; 
    }
    
    //
    public final static Move get(long h)
    {
        //
        if (CACHE_LEGALS) 
        {
            return CACHE.get(h);
        }
        
        //
        return null;
    }
    
    //
    public final static void dump()
    {
        //
        print("Legals (size:"+CACHE.size()+")");
        
        //
        CACHE.entrySet().stream().map((i) -> {
            print(Long.toHexString(i.getKey()));
            return i;
        }).forEach((i) -> {    
            print(desc(i.getValue()));
        });
    }
    
    //
    public final static void info()
    {
        //
        print("Legals (size:"+CACHE.size()+" q:"+queries+" s:"+success+")");
    }   
    
    public final static int size()
    {
        return CACHE.size();
    }
}
