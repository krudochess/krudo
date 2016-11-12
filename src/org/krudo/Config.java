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
    public static boolean 

    /**
     * CACHING SETTINGS
     * 
     * disable or enable cache for every module
     */

    // cache eval function results  
    CACHE_EVAL = true,   

    // cache legals moves
    CACHE_LEGALS = true,

    // cache captures moves
    CACHE_CAPTURES = true,

    /**
    * EVALUATION SETTINGS
    * 
    * manage every evaluation activity 
    */

    //    
    EVAL_NODE = true,
        
    // of legal moves generated    
    EVAL_LEGALS = true,

    // of legal moves generated        
    EVAL_MVV_LAA = false,

    // of legal moves generated        
    EVAL_CAPTURE = false,

    // of legal moves generated
    EVAL_POSITIONAL = true,        

    // of legal moves generated
    EVAL_TAPERED_ENDING = false,        

    // of legal moves generated
    EVAL_TAPERED_OPENING = false,        

    // of legal moves generated
    EVAL_MOBILITY = false,        

    /**
     * SEARCHING SETTINGS
     * 
     * asdads
     */
    
    //
    SEARCH_BRUTE_FORCE = false,
            
    //
    SEARCH_QUIESCENCE = true,
    
    //
    SEARCH_ASPIRATION = true,
    
    //
    SEARCH_CONTROL = true,        
            
    //
    SEARCH_THREAD = false,
            
    //
    SEARCH_UPDATE = true,
            
    //
    SEARCH_TT = false,
            
    /**
     * PV STACK SETTINGS
     * 
     * asdads
     */
            
    //
    PV_CAT = true,
                        
    /**
     * MOVE STACK SETTINGS
     * 
     * asdads
     */
        
    // of legal moves generated
    MOVE_LEGALS = true,
            
    // into move stack used for search        
    MOVE_SORT = true, 
    
    // into move stack used for search        
    MOVE_TWIN = true, 
                
    /**
     * REMAPS SETTINGS
     * 
     * asdads
     */
      
    //
    NODE_CAPTURES = true,

    /**
     * REMAPS SETTINGS
     * 
     * asdads
     */
      
    //
    REMAPS_EVAL = false,

    //
    REMAPS_PSEUDO = false;
}
