package org.krudo.cache;

//
import static org.krudo.Const.*;

//
import java.util.LinkedHashMap;

//
public class Evals extends LinkedHashMap<Long,Integer> {
	// 
	public Evals() {		

		//
		super(EVAL_CACHE_SIZE, 0.95f, true);	
	}

	//
	public final void add(long h, int w) {			

		//
		if (EVAL_CACHE) { 
			
			//
			put(h, w);				
		}		
	}
	
	//
	public final boolean has(long h) {
		
		//
		if (EVAL_CACHE) {
			return containsKey(h);
		} 
		
		//
		else {
			return false;
		}
	}
}
