/**
 * Krudo 0.18a - Java chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo;

//
import java.util.Map;
import java.util.LinkedHashMap;

//
import static org.krudo.Config.*;

//    
public class Legals 
{    
    //
    private static final int CACHE_SIZE = 250000;
    
    //
    private static int queries = 0;

    //
    private static int success = 0;
               
    //
    private final static Cache CACHE = new Cache();
    
    //
    private final static class Cache extends LinkedHashMap<Long, Move> 
    {
        //
        public Cache() 
        {
            //
            super(CACHE_SIZE, 1, true);
        }
        
        //
        @Override
        protected boolean removeEldestEntry(Map.Entry<Long, Move> e) 
        {
            //
            if (!CACHE_LEGALS) { return false; }
            
            // 
            if (size() > CACHE_SIZE) 
            {
                //
                Moves.free(e.getValue());

                // return true than remove
                return true;                                                    
            }
            
            // return false not remove
            return false; 
        }
    }
       
    //
    public final static void add(
        final long h, 
        final Move m
    ) {           
        //
        if (!CACHE_LEGALS) { return; } 

        //
        CACHE.put(h, m);                
    }

    //
    public final static boolean has(
        final long h
    ) {    
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
    public final static Move get(
        final long h
    ) {
        //
        if (!CACHE_LEGALS) { return null; } 
        
        //
        return CACHE.get(h);        
    }    
}
