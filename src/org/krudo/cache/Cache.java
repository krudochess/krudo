/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.cache;

// required non-static class
import org.krudo.cache.Evals;
import org.krudo.cache.Tables;
import org.krudo.cache.Legals;

// global cache
public final class Cache {

    // cache evaluation value hash
    public static final Evals evals = new Evals();    
    
    // cache evaluation value hash
    public static final Tables tables = new Tables();    

    // cache legal moves of a node based on hash
    public static final Legals legals = new Legals();        
}
