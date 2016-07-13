/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo;

//
public final class Config 
{    
    // config flags
    public final static boolean 
    
            
    CACHE_LEGALS = true,
    CACHE_EVAL = true,      
            
    

    /**
     * EVALUATION CONFIG
     * 
     * manage every evaluation activity
     * 
     * 
     */
        
    //    
    EVAL_NODE = true,
       
    // of legal moves generated
    EVAL_LEGALS = false,     
    
    // enable/disable evaluation 
    // of legal moves generated        
    EVAL_MVV_LAA = false,
            
    // enable/disable evaluation 
    // of legal moves generated        
    EVAL_CAPTURE = false,
                        
    // enable/disable evaluation 
    // of legal moves generated
    EVAL_POSITIONAL = true,        
    
    // enable/disable evaluation 
    // of legal moves generated
    EVAL_TAPERED_ENDING = false,        
    
    // enable/disable evaluation 
    // of legal moves generated
    EVAL_TAPERED_OPENING = false,        
        
        EVAL_REMAPS = false,
        
        
        
                
    // enable/disable evaluation 
    // of legal moves generated
    MOVE_LEGALS = true,
            
    // enable/disable sorting function 
    // into move stack used for search        
    MOVE_SORT = true, 
    

            

            
   
   
    MOVE_EVAL        = true, // use threats in run-time
    MOVE_CACHE        = false,    // use caching systems (TT, Zobrist, ecc...)
    MOVE_BUFFER        = true,
    SEARCH_CUT_OFF    = true,
    PSEUDO_REMAPS    = false,
    THREAD            = false, // use threats in run-time
    TABLE            = false, // use threats in run-time
    ASPIRATION        = false;
}
