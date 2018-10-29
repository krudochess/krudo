/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.krudo;

import java.util.LinkedHashMap;
import java.util.Map;
import static org.krudo.Config.CACHE_EVAL;

/**
 *
 * @author francescobianco
 */
public class Positions {
     
 //
   //
    private static final int EVAL_POSITION_SIZE = 50000;
    
   
   
//
    private final static POSITION_CACHE POSITION = new POSITION_CACHE();
      
    //
    static class POSITION_CACHE extends LinkedHashMap<Long, Integer>    
    {
        //
        public int queries = 0;
        
        //
        public int success = 0;
        
        //
        public POSITION_CACHE()
        {
            super(EVAL_POSITION_SIZE, 1.1f, true);        
        }
        
        //
        @Override
        protected boolean removeEldestEntry(Map.Entry<Long, Integer> e) 
        {
            //
            return size() > EVAL_POSITION_SIZE; 
        }
        
        //
        public final void add(long h, int m)
        {           
            //
            if (CACHE_EVAL) 
            { 
                //
                super.put(h, m);                
            }        
        }

        //
        public final boolean has(long h) 
        {    
            //
            queries++;

            //
            if (CACHE_EVAL && super.containsKey(h)) 
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
        public final int get(long h)
        {
            //
            if (CACHE_EVAL) 
            {
                //
                return super.get(h);
            }

            //
            return 0;
        }
    };
   
}
