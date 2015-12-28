package org.krudo;

// required non-static class
import org.krudo.cache.Eval;
import org.krudo.cache.Table;
import org.krudo.cache.Legals;

// global cache
public final class Cache {

	// cache evaluation value hash
	public static final Eval eval = new Eval();	
	
	// cache evaluation value hash
	public static final Table table = new Table();	

	// cache legal moves of a node based on hash
	public static final Legals legals = new Legals();		
}
