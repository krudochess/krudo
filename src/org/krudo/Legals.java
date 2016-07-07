/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo;

//
import org.krudo.Move;

//
import java.util.Map;

import java.util.LinkedHashMap;

//
import static org.krudo.util.Tool.*;
import static org.krudo.util.Debug.*;
import static org.krudo.util.Describe.*;
import static org.krudo.Config.*;
import static org.krudo.Constant.*;

//    
public class Legals 
{    
    //
    private static final int LEGALS_CACHE_SIZE = 100000;
    
    //
    private static int queries = 0;
    private static int success = 0;
    
    //
    private final static LinkedHashMap<Long, Move> 
    MOVES = new LinkedHashMap<Long, Move> (LEGALS_CACHE_SIZE, 0.95f, true) 
    {
        @Override
        protected boolean removeEldestEntry(Map.Entry<Long, Move> e) 
        {
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
    public final static void add(Long h, Move m)
    {           
        //
        if (CACHE_LEGALS) 
        { 
            //
            MOVES.put(h, m);                
        }        
    }

    //
    public final static boolean has(long h) 
    {    
        //
        queries++;
        
        if (CACHE_LEGALS && MOVES.containsKey(h)) 
        {
            success++;
        
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
            return MOVES.get(h);
        }
        
        //
        return null;
    }
    
    //
    public final static void dump()
    {
        //
        print("Legals (size:"+MOVES.size()+")");
        
        for (Map.Entry<Long, Move> i: MOVES.entrySet())
        {
            print(Long.toHexString(i.getKey()));
            print(desc(i.getValue()));            
        }    
    }
    
    //
    public final static void info()
    {
        //
        print("Legals (size:"+MOVES.size()+" q:"+queries+" s:"+success+")");
    }    
}
