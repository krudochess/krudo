
  /*\
 / + \ Krudo 0.20a - the messianic chess engine.
 \IHS/ by Francesco Bianco <bianco@javanile.org>
  \*/

// krudo package
package org.krudo;

// 
import java.util.Map;
import java.util.LinkedHashMap;

//
import static org.krudo.Config.*;

//
public final class Captures 
{
    //
    private static int
    count = 0,
    queries = 0,
    success = 0;

    //
    private final static int
    CACHE_SIZE = (MEMORY_BUFFER << MEMORY_FACTOR >> 1) - 1,
    STACK_SIZE = CACHE_SIZE + 30;
    
    //
    private final static Capture[] STACK = new Capture[STACK_SIZE];
    
    //
    private final static LinkedHashMap<Long, Capture>
    CACHE = new LinkedHashMap<Long, Capture>(CACHE_SIZE, 1, false)
    {
        // check if cache is full and flush oldest item
        @Override 
        protected boolean removeEldestEntry(Map.Entry<Long, Capture> e) 
        {
            // cache is full
            if (size() > CACHE_SIZE) 
            {
                // return entry to stack
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
        for (int i = 0; i < STACK_SIZE; i++)
        {
            STACK[i] = new Capture();
            if (i == 0) {
                CACHE.put(0L, STACK[i]);
                CACHE.remove(0L);
            }
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
    
    // get item from cache by hash
    public final static Capture get(
        final long h
    ) {
        //
        return CACHE.get(h);
    }
    
    // add item into cache by hash
    public static void add(long h, Capture c)
    {
        //
        CACHE.put(h, c);
    }
    
    //
    public static void del(long h)
    {
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
