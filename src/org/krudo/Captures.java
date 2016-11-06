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
    private final static int CAPTURES_CACHE_SIZE = 150000;
            
    //
    private final static int CAPTURES_STACK_SIZE = 150100;
    
    //
    private final static Capture[] STACK = new Capture[CAPTURES_STACK_SIZE];
    
    //
    private final static LinkedHashMap<Long, Capture> 
    CACHE = new LinkedHashMap<Long, Capture> (CAPTURES_CACHE_SIZE, 1.1f, false) 
    {
        @Override
        protected boolean removeEldestEntry(Map.Entry<Long, Capture> e) 
        {
            //
            if (!CACHE_CAPTURES) { return false; }

            // 
            if (size() > CAPTURES_CACHE_SIZE) 
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
        for (int i = 0; i < CAPTURES_STACK_SIZE; i++)
        {
            STACK[i] = new Capture(); 
        }
        
        //
        count = CAPTURES_STACK_SIZE;
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
    public static void info()
    {
        int verified = count + CACHE.size();
        print(
            "Captures",
            "free="+count,
            "cache="+CACHE.size(),
            "verified="+verified,
            "q="+queries,
            "s="+success
        );
    }
}
