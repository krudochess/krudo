
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
public class Legals 
{    
    //
    private static final int
    SIZE = (MEMORY_BUFFER << MEMORY_FACTOR) - 1;
    
    //
    private static int
    queries = 0,
    success = 0;

    private final static LinkedHashMap<Long, Move>
    CACHE = new LinkedHashMap<Long, Move>(SIZE + 1, 1f, false)
    {
        //
        @Override
        protected boolean removeEldestEntry(Map.Entry<Long, Move> e)
        {
            //
            if (size() > SIZE)
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
    public final static void init()
    {
        CACHE.put(0L, new Move());
        CACHE.remove(0L);
    }

    //
    public final static void add(
        final long h, 
        final Move m
    ) {           
        //
        CACHE.put(h, m);                
    }

    //
    public final static boolean has(
        final long h
    ) {    
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
        return CACHE.get(h);        
    }

    //
    public final static void del(
        final long h
    ) {
        //
        CACHE.remove(h);
    }
}
