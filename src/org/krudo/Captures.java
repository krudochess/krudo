/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

// krudo package
package org.krudo;

// required static class
import static org.krudo.Tool.*;
import static org.krudo.Config.*;

// 
import java.util.Map;
import java.util.LinkedHashMap;

//
public final class Captures 
{
    //
    private static int count = 0;

    //
    private static int queries = 0;

    //
    private static int success = 0;

    //
    private final static int CACHE_SIZE = 131071;
            
    //
    private final static int STACK_SIZE = 131172;
    
    //
    private final static Capture[] STACK = new Capture[STACK_SIZE];
    
    //
    private final static Cache CACHE = new Cache();
    
    //
    private final static class Cache extends LinkedHashMap<Long, Capture> 
    {
        //
        public Cache()
        {
            //
            super(CACHE_SIZE + 1, 1, false);
        }
        
        //
        @Override
        protected boolean removeEldestEntry(Map.Entry<Long, Capture> e) 
        {
            //
            if (!CACHE_CAPTURES) { return false; }

            // 
            if (size() > CACHE_SIZE) 
            {
                //
                Captures.free(e.getValue());

                // return true than remove
                return true;                                                    
            }
            // return false not remove
            return false; 
        }
    };
    
    //
    public static void init()
    {
        //
        if (CACHE_CAPTURES) {
            CACHE.put(0L, new Capture());
            CACHE.remove(0L);
        }
        
        //
        for (int i = 0; i < STACK_SIZE; i++)
        {
            STACK[i] = new Capture(); 
        }
        
        //
        count = STACK_SIZE;
    }
    
    //
    public static Capture pick() 
    {
        //
        return STACK[--count];
    }
    
    //
    public static void free(final Capture capture) 
    {
        //
        capture.count = 0;
        
        //
        STACK[count++] = capture;
    }
    
    //
    public static boolean has(long h)
    {
        //
        if (!CACHE_CAPTURES) { return false; }
        
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
    public static Capture get(long h)
    {
        //
        if (!CACHE_CAPTURES) { return null; } 

        //
        return CACHE.get(h);
    }
    
    //
    public static void add(long h, Capture c)
    {
        //
        if (!CACHE_CAPTURES) { return; } 

        //
        CACHE.put(h, c);
    }
    
    //
    public static void del(long h)
    {
        //
        if (!CACHE_CAPTURES) { return; } 

        //
        if (!CACHE.containsKey(h)) { return; }
        
        //
        Capture c = CACHE.get(h);
        
        //
        CACHE.remove(h);
        
        //
        free(c);
    }
}
