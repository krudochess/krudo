/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.krudo;

import java.util.LinkedHashMap;
import java.util.Map;
import static org.krudo.Config.CACHE_EVAL;
import static org.krudo.Zobrist.hash_material;

/**
 *
 * @author francescobianco
 */
public class Matirials 
{
    // matirial hashing stored for
    private final static int[][] MA = {
        //bp wp bn wn bb wb br wr bq wq bk wk    score
        { 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1,    +0   }, // K+B  vs K   
        { 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1,    +0   }, // K    vs k+b  
        { 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1,    +0   }, // K+N  vs k 
        { 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1,    +0   }, // K    vs k+n 
        { 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 1, 1,    +0   }, // K+2N vs k 
        { 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 1, 1,    +0   }, // K    vs k+2n 
    };
    
    //
    private static final int CACHE_SIZE = 1000;
     
    //
    private final static MATERIAL_CACHE MATERIAL = new MATERIAL_CACHE(); 
    
    //
    static class MATERIAL_CACHE extends LinkedHashMap<Long, Integer> 
    {
        //
        public int queries = 0;
        
        //
        public int success = 0;
        
        //
        public MATERIAL_CACHE() 
        {
            //
            super(EVAL_MATERIAL_SIZE, 1.1f, true);
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
     
    // init
    public final static void init()
    {
        // apply cache matirial hash
        for (int[] material : MA) {
            MATERIAL.add(hash_material(material), material[12]);
        }
    }
}
