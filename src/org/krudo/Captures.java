/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo;

//
import java.util.LinkedHashMap;
import java.util.Map;
import static org.krudo.Tool.*;

//
public class Captures 
{
    //
    public final static int CAPTURES_STACK_SIZE = 100100;
    
    //
    public final static int CAPTURES_CACHE_SIZE = 100000;
    
    //
    private static int count = 0;
    
    //
    private final static Capture[] STACK = new Capture[CAPTURES_STACK_SIZE];
    
    //
    private final static LinkedHashMap<Long, Capture> 
    CACHE = new LinkedHashMap<Long, Capture> (CAPTURES_CACHE_SIZE, 0.95f, true) 
    {
        @Override
        protected boolean removeEldestEntry(Map.Entry<Long, Capture> e) 
        {
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
        capture.i = 0;
        
        //
        STACK[count++] = capture;
    }
    
    //
    public static boolean has(long h)
    {
        //
        return CACHE.containsKey(h);
    }
    
    //
    public static Capture get(long h)
    {
        //
        return CACHE.get(h);
    }
    
    //
    public static void add(long h, Capture c)
    {
        //
        CACHE.put(h, c);
    }
    
    //
    public static void info()
    {
        int verified = count + CACHE.size();
        print("Captures free="+count+" cache="+CACHE.size()+" verified="+verified);
    }
}
