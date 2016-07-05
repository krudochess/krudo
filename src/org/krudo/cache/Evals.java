/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.cache;

//
import java.util.LinkedHashMap;

//
import static org.krudo.Constant.*;

//
public class Evals extends LinkedHashMap<Long,Integer> 
{
    // 
    public Evals() 
    {        
        //
        super(EVAL_CACHE_SIZE, 0.95f, true);    
    }

    //
    public final void add(long h, int w) 
    {    
/*        
        //
        if (EVAL_CACHE) 
        { 
            //
            put(h, w);                
        }     */   
    }
    
    //
    public final boolean has(long h) 
    {
       /* //
        if (EVAL_CACHE) {
            return containsKey(h);
        } 
        
        //
        else {
            return false;
        }*/
        return false;
    }
}
